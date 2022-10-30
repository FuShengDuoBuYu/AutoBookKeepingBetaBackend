package com.example.autobookkeepingbetabackend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.autobookkeepingbetabackend.Entity.Orders;
import com.example.autobookkeepingbetabackend.Entity.User;
import com.example.autobookkeepingbetabackend.Service.Impl.OrderServiceImpl;
import com.example.autobookkeepingbetabackend.Service.OrderMapPlaceService;
import com.example.autobookkeepingbetabackend.Service.OrderService;
import com.example.autobookkeepingbetabackend.Service.UserService;
import com.example.autobookkeepingbetabackend.Util.Response;
import com.example.autobookkeepingbetabackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class OrderController {

    OrderService orderService;
    UserService userService;
    OrderMapPlaceService orderMapPlaceService;

    @Autowired
    public OrderController(OrderService orderService,UserService userService,OrderMapPlaceService orderMapPlaceService){
        this.userService = userService;
        this.orderService = orderService;
        this.orderMapPlaceService = orderMapPlaceService;
    }

    //添加账单
    @PostMapping("/addOrder")
    public Response<?> addOrder(@RequestBody JSONObject addOrderJson){
        Integer year = (Integer) addOrderJson.get("year");
        Integer month = (Integer) addOrderJson.get("month");
        Integer day = (Integer) addOrderJson.get("day");
        String clock = (String) addOrderJson.get("clock");
        Double money = Double.valueOf(addOrderJson.get("money").toString());
        String bankName = (String) addOrderJson.get("bankName");
        String orderRemark = (String) addOrderJson.get("orderRemark");
        String costType = (String) addOrderJson.get("costType");
        String userId = (String) addOrderJson.get("userId");

        Orders addOrder = new Orders(year,month,day,clock,money,bankName,orderRemark,costType,userId);

        return orderService.saveOrder(addOrder);
    }

    //修改账单
    @PutMapping("/modifyOrder/{id}")
    public Response<?> modifyOrder(@RequestBody JSONObject addOrderJson,@PathVariable int id){

        Integer month = (Integer) addOrderJson.get("month");
        Integer day = (Integer) addOrderJson.get("day");
        String clock = (String) addOrderJson.get("clock");
        Double money = Double.valueOf(addOrderJson.get("money").toString());
        String bankName = (String) addOrderJson.get("bankName");
        String orderRemark = (String) addOrderJson.get("orderRemark");
        String costType = (String) addOrderJson.get("costType");

        //根据主键id找到对应的账单
        Orders pendingModifyOrder = orderService.getOrderById(id);
        //修改
        pendingModifyOrder.setMonth(month);
        pendingModifyOrder.setDay(day);
        pendingModifyOrder.setClock(clock);
        pendingModifyOrder.setMoney(money);
        pendingModifyOrder.setBankName(bankName);
        pendingModifyOrder.setOrderRemark(orderRemark);
        pendingModifyOrder.setCostType(costType);

        //保存
        return orderService.saveOrder(pendingModifyOrder);
    }

    //删除账单
    @DeleteMapping("/deleteOrder/{id}")
    public Response<?> deleteOrder(@PathVariable int id){
        //删除地理位置
        orderMapPlaceService.deleteOrderMapPlaceByOrderId(id);
        Integer delete=orderService.deleteOrderById(id);
        return new Response<>(delete==1,delete==1?"删除成功":"删除失败,请检查是否有该账单");
    }

    //查询家庭账单
    @GetMapping("/findMonthFamilyOrders/{familyId}/{month}")
    public Response<?> findMonthFamilyOrders(@PathVariable String familyId,@PathVariable String month){
        List<List<?>> familyUersAndFamilyMembers = new ArrayList<>();

        //先找到家庭的用户
        List<User> familyUsers = (List<User>) userService.getUsersByFamilyId(familyId).getData();
        //在根据用户的id查找到所有的账单
        List<Orders> familyOrders = new ArrayList<>();
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        for(int i = 0;i < familyUsers.size();i++){
            List<Orders> userOrders = (List<Orders>) orderService.findOrdersByUserIdAndMonthAndYear(familyUsers.get(i).getPhoneNum(),Integer.valueOf(month),Integer.valueOf(year));
            familyOrders.addAll(userOrders);
        }
        //排序一下,按照年月日排序
        Collections.sort(familyOrders,(a,b)->{
            if(a.getYear()!=b.getYear()){
                return b.getYear()-a.getYear();
            }
            else if(a.getMonth()!=b.getMonth()){
                return b.getMonth()-a.getMonth();
            }
            else {
                return b.getDay() - a.getDay();
            }
        });
        familyUersAndFamilyMembers.add(familyOrders);
        familyUersAndFamilyMembers.add(familyUsers);
        return new Response<>(true,"查询成功",familyUersAndFamilyMembers);
    }

    //查询家庭各个月份的收支
    @GetMapping("/findFamilyAllMonthCosts/{familyId}")
    public Response<?> findFamilyMonthCosts(@PathVariable String familyId){
        //先找到家庭的用户
        List<User> familyUsers = (List<User>) userService.getUsersByFamilyId(familyId).getData();
        //查看当前的年份月份
        Calendar date = Calendar.getInstance();
        Integer year = date.get(Calendar.YEAR);
        Integer month = date.get(Calendar.MONTH)+1;
        //创建一个数组,用来存储每个月的收支
        List<Double> familyMonthCosts = new ArrayList<>();
        //记录12个月的收支
        for(int i = 0;i < 12;i++){
            Double monthCost = 0.0;
            for(int j = 0;j < familyUsers.size();j++){
                //根据用户id和年月查找到对应的账单
                List<Orders> userOrders = (List<Orders>) orderService.findOrdersByUserIdAndMonthAndYear(familyUsers.get(j).getPhoneNum(),month,year);
                for(int k = 0;k < userOrders.size();k++){
                    if(userOrders.get(k).getCostType().equals("支出")){
                        monthCost -= userOrders.get(k).getMoney();
                    }
                    else {
                        monthCost += userOrders.get(k).getMoney();
                    }
                }
            }
            familyMonthCosts.add(monthCost);
            month--;
            if(month == 0){
                month = 12;
                year--;
            }
        }
        return new Response<>(true,"查询成功",familyMonthCosts);
    }

    //查询家庭某个月的支出情况
    @GetMapping("/findFamilySomeMonthCosts/{familyId}/{year}/{month}")
    public Response<?> findFamilySomeMonthCosts(@PathVariable String familyId,@PathVariable Integer year,@PathVariable Integer month){
        //先找到家庭的用户
        List<User> familyUsers = (List<User>) userService.getUsersByFamilyId(familyId).getData();
        //将该家庭的该月账单按照支出类型分组
        Map<String,List<Orders>> familySomeMonthCosts = new HashMap<>();
        for(int i = 0;i < familyUsers.size();i++){
            //根据用户id和年月查找到对应的账单
            List<Orders> userOrders = (List<Orders>) orderService.findOrdersByUserIdAndMonthAndYear(familyUsers.get(i).getPhoneNum(),month,year);
            for(int j = 0;j < userOrders.size();j++){
                if(familySomeMonthCosts.containsKey(userOrders.get(j).getCostType())){
                    familySomeMonthCosts.get(userOrders.get(j).getCostType()).add(userOrders.get(j));
                }
                else {
                    List<Orders> orders = new ArrayList<>();
                    orders.add(userOrders.get(j));
                    familySomeMonthCosts.put(userOrders.get(j).getCostType(),orders);
                }
            }
        }
        //删除map中的收入
        familySomeMonthCosts.remove("收入");
        return new Response<>(true,"查询成功",familySomeMonthCosts);
    }

    //搜索查询账单信息
    @PostMapping("/searchOrders")
    public Response<?> searchOrders(@RequestBody JSONObject searchOrdersJson){
        String mode = searchOrdersJson.getString("mode");
        String familyId = searchOrdersJson.getString("familyId");
        String userId = searchOrdersJson.getString("userId");
        String year = searchOrdersJson.getString("year");
        String month = searchOrdersJson.getString("month");
        String day = searchOrdersJson.getString("day");
        String searchOrderRemark = searchOrdersJson.getString("searchOrderRemark");
        String stringSearchCostType = searchOrdersJson.getString("searchCostType").substring(1,searchOrdersJson.getString("searchCostType").length()-1);
        String[] searchCostType = stringSearchCostType.split(", ");
        Boolean ifIgnoreYear = searchOrdersJson.getBoolean("ifIgnoreYear");
        Boolean ifIgnoreMonth = searchOrdersJson.getBoolean("ifIgnoreMonth");
        Boolean ifIgnoreDay = searchOrdersJson.getBoolean("ifIgnoreDay");
        //打印所有信息
        System.out.println("mode:"+mode);
        System.out.println("familyId:"+familyId);
        System.out.println("userId:"+userId);
        System.out.println("year:"+year);
        System.out.println("month:"+month);
        System.out.println("day:"+day);
        System.out.println("searchOrderRemark:"+searchOrderRemark);
        System.out.println("stringSearchCostType"+stringSearchCostType);
        System.out.println("searchCostType:"+searchCostType);
        System.out.println("ifIgnoreYear:"+ifIgnoreYear);
        System.out.println("ifIgnoreMonth:"+ifIgnoreMonth);
        System.out.println("ifIgnoreDay:"+ifIgnoreDay);

        List<Orders> searchOrders = new ArrayList<>();
        Map<String,Object> result = new HashMap<>();
        //家庭版
        if(mode.equals("家庭版")){
            //找到家庭的所有用户
            List<User> familyUsers = (List<User>) userService.getUsersByFamilyId(familyId).getData();
            //遍历所有用户
            for(User user:familyUsers){
                String familyUserId = user.getPhoneNum();
                if(ifIgnoreYear){
                    searchOrders.addAll(orderService.findOrdersByUserIdAndOrderRemarkContainsAndCostType(familyUserId,searchOrderRemark,searchCostType));
                }
                else if(ifIgnoreMonth){
                    searchOrders.addAll(orderService.findOrdersByUserIdAndYearAndOrderRemarkContainsAndCostType(familyUserId,Integer.parseInt(year),searchOrderRemark,searchCostType));
                }
                else if(ifIgnoreDay){
                    searchOrders.addAll(orderService.findOrdersByUserIdAndYearAndMonthAndOrderRemarkContainsAndCostType(familyUserId,Integer.parseInt(year),Integer.parseInt(month),searchOrderRemark,searchCostType));
                }
                else {
                    searchOrders.addAll(orderService.findOrdersByUserIdAndYearAndMonthAndDayAndOrderRemarkContainsAndCostType(familyUserId,Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day),searchOrderRemark,searchCostType));
                }
            }
            //用户信息
            ArrayList<User> users = new ArrayList<>();
            users.addAll(familyUsers);
            result.put("userInfo",users);
            //对账单时间排序
            Collections.sort(searchOrders,(a,b)->{
                if(a.getYear()!=b.getYear()){
                    return b.getYear()-a.getYear();
                }
                else if(a.getMonth()!=b.getMonth()){
                    return b.getMonth()-a.getMonth();
                }
                else {
                    return b.getDay() - a.getDay();
                }
            });
            result.put("ordersInfo",searchOrders);
        }
        //个人版
        else{
            //全部账单
            if(ifIgnoreYear){
                searchOrders = orderService.findOrdersByUserIdAndOrderRemarkContainsAndCostType(userId,searchOrderRemark,searchCostType);
            }
            //某年账单
            else if(ifIgnoreMonth){
                searchOrders = orderService.findOrdersByUserIdAndYearAndOrderRemarkContainsAndCostType(userId,Integer.parseInt(year),searchOrderRemark,searchCostType);
            }
            //某月账单
            else if(ifIgnoreDay){
                searchOrders = orderService.findOrdersByUserIdAndYearAndMonthAndOrderRemarkContainsAndCostType(userId,Integer.parseInt(year),Integer.parseInt(month),searchOrderRemark,searchCostType);
            }
            //某日账单
            else {
                //账单信息
                searchOrders = orderService.findOrdersByUserIdAndYearAndMonthAndDayAndOrderRemarkContainsAndCostType(userId,Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day),searchOrderRemark,searchCostType);
            }
            //用户信息
            ArrayList<User> users = new ArrayList<>();
            users.add(userService.getUserByPhoneNum(userId));
            result.put("userInfo",users);
            result.put("ordersInfo",searchOrders);
        }
        return new Response<>(true,"查询成功",result);
    }
}
