package com.diandianzuan.aso.diandianzuan.net;


import com.diandianzuan.aso.diandianzuan.manager.RequestManager;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * 网络请求描述接口
 */
public interface RetrofitRequestInterface {


    /*获取验证码*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"GetCode")
    Call<String> getCode(@FieldMap Map<String, String> params);
    /*我的*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"GetUserinfo")
    Call<String> GetUserinfo(@FieldMap Map<String, String> params);














    @FormUrlEncoded
    @POST("selectInfo/getAboutUs.do")
    Call<String> getAboutUs(@Field("type") int type);

//    @FormUrlEncoded   //请求和响应格式注解
//    @POST("index/firstTimeLookMe.do")
//        //请求方法注解
//    Call<String> getUserInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("index.php?g=Admin&m=app2&a=Mine")
    Call<String> getQCCUserInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Call<String> getZXBUserInfo(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded   //请求和响应格式注解
    @POST("index/getIndexCitys.do")
        //请求方法注解
    Call<String> getIndexCitys(@FieldMap Map<String, String> params);






    /*获取验证码2*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"VerificationCode2")
    Call<String> getCode2(@FieldMap Map<String, String> params);
    /*绑定手机号获取验证码*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"VerificationCode3")
    Call<String> getCodeBindPhone(@FieldMap Map<String, String> params);
    /*用户注册*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"Register")
    Call<String> register(@FieldMap Map<String, String> params);
    /*用户登录*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"Login")
    Call<String> login(@FieldMap Map<String, String> params);
    /*忘记密码/修改密码*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"RetrievePassword")
    Call<String> retrievePassword(@FieldMap Map<String, String> params);

    /*首页*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"Homepage")
    Call<String> homepage(@FieldMap Map<String, String> params);
    /*商品列表*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"Productlist")
    Call<String> productlist(@FieldMap Map<String, String> params);
    /*搜索栏历史记录*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"SearchHistoryList")
    Call<String> searchHistoryList(@FieldMap Map<String, String> params);
    /*清除历史记录*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"DeleteHistoryList")
    Call<String> deleteHistoryList(@FieldMap Map<String, String> params);
    /*商品详情*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"ProductDetail")
    Call<String> productDetail(@FieldMap Map<String, String> params);

    /*收藏*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"DoCollect")
    Call<String> doCollect(@FieldMap Map<String, String> params);
    /*加入购物车*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"AddtoCart")
    Call<String> addToCart(@FieldMap Map<String, String> params);
    /*购物车列表*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"ShoppingList")
    Call<String> shoppingList(@FieldMap Map<String, String> params);
    /*购物车中商品数量修改*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"UpdateCart")
    Call<String> updateCart(@FieldMap Map<String, String> params);
    /*购物车中商品删除*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"DeleteCart")
    Call<String> deleteCart(@FieldMap Map<String, String> params);
    /*切换城市*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"ChooseCity")
    Call<String> chooseCity(@FieldMap Map<String, String> params);
    /*根据省市区返回专卖店*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"choose_users")
    Call<String> chooseUsers(@FieldMap Map<String, String> params);


    /*我的收货地址*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"MyShippingaddress")
    Call<String> myShippingaddress(@FieldMap Map<String, String> params);
    /*添加收货地址*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"AddShippingaddress")
    Call<String> AddShippingaddress(@FieldMap Map<String, String> params);
    /*编辑收货地址*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"EditShippingaddress")
    Call<String> editShippingaddress(@FieldMap Map<String, String> params);
    /*删除收货地址*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"DeleteShippingaddress")
    Call<String> deleteShippingaddress(@FieldMap Map<String, String> params);



    /*立即购买(直接购买和从购物车下单)*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"Buynow")
    Call<String> buynow(@FieldMap Map<String, String> params);
    /*修改绑定门店*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"ChangeUser")
    Call<String> changeUser(@FieldMap Map<String, String> params);
    /*提交订单*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"SubmitOrder")
    Call<String> submitOrder(@FieldMap Map<String, String> params);
    /*提交订单套餐*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"SubmitOrder_package")
    Call<String> SubmitOrder_package(@FieldMap Map<String, String> params);
    /*我的订单*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"MyOrder")
    Call<String> myOrder(@FieldMap Map<String, String> params);
    /*订单详情(待结算、待发货、待收货、已完成)*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"OrderDetail")
    Call<String> orderDetail(@FieldMap Map<String, String> params);
    /*取消订单*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"CancelOrder")
    Call<String> cancelOrder(@FieldMap Map<String, String> params);

    /*我的收藏*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"MyCollection")
    Call<String> myCollection(@FieldMap Map<String, String> params);



    /*“我的收藏”里的（删除）*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"DeleteMycollection")
    Call<String> deleteMycollection(@FieldMap Map<String, String> params);
    /*意见反馈*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"AddFeedback")
    Call<String> addFeedback(@FieldMap Map<String, String> params);
    /*更新个人头像*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"UpdateUserPic")
    Call<String> updateUserPic(@FieldMap Map<String, String> params);
    /*修改昵称*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"UpdateUsernicename")
    Call<String> updateUsernicename(@FieldMap Map<String, String> params);
    /*修改性别*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"UpdateUserSex")
    Call<String> updateUserSex(@FieldMap Map<String, String> params);
    /*组织架构（待激活，待安排）*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"InvitedPerson")
    Call<String>  invitedPerson(@FieldMap Map<String, String> params);
    /*立即购买套餐(立即购买)*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"Buynow_package")
    Call<String>   buynow_package(@FieldMap Map<String, String> params);



    /*搜索节点人*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"SearchPoint")
    Call<String>   SearchPoint(@FieldMap Map<String, String> params);
    /*安排某个人的点位(安排层)*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"Arrange_point")
    Call<String>   Arrange_point(@FieldMap Map<String, String> params);
    /*提交安排某个人的点位(安排层)*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"Submit_point")
    Call<String>   Submit_point(@FieldMap Map<String, String> params);
    /*点位信息*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"PointDetail")
    Call<String>   PointDetail(@FieldMap Map<String, String> params);
    /*TeamMember*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"TeamMember")
    Call<String>   TeamMember(@FieldMap Map<String, String> params);
    /*myemoney*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"myemoney")
    Call<String>   myemoney(@FieldMap Map<String, String> params);
    /*transfer*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"transfer")
    Call<String>   transfer(@FieldMap Map<String, String> params);
    /*emoneyrecord*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"emoneyrecord")
    Call<String>   emoneyrecord(@FieldMap Map<String, String> params);
    /*moneyout*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"moneyout")
    Call<String>   moneyout(@FieldMap Map<String, String> params);
    /*VerificationCode4*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"VerificationCode4")
    Call<String>   VerificationCode4(@FieldMap Map<String, String> params);
    /*提现记录*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"moneyoutlist")
    Call<String>   moneyoutlist(@FieldMap Map<String, String> params);
    /*提现详情*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"moneyoutdetail")
    Call<String>   moneyoutdetail(@FieldMap Map<String, String> params);
    /*获取手续费比例*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"getpeizhi")
    Call<String>   getpeizhi(@FieldMap Map<String, String> params);
    /*提现再次提交*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"remoneyout")
    Call<String>   remoneyout(@FieldMap Map<String, String> params);
    /*修改开户行*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"changebank")
    Call<String>   changebank(@FieldMap Map<String, String> params);
    /*修改银行卡号*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"changebankcard")
    Call<String>   changebankcard(@FieldMap Map<String, String> params);
    /*修改身份证*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"changepersonnumber")
    Call<String>   changepersonnumber(@FieldMap Map<String, String> params);
    /*获取团队成员代数*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"getdlevel")
    Call<String>   getdlevel(@FieldMap Map<String, String> params);
    /*关于我们，手续费*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"gettext")
    Call<String>   gettext(@FieldMap Map<String, String> params);
    /*层数网体*/
    @FormUrlEncoded
    @POST(RequestManager.mInterfacePrefix+"wangti")
    Call<String>   wangti(@FieldMap Map<String, String> params);
}
