package com.mtj.common.base

import android.app.Activity
import android.content.Context
import java.util.*

object ActivityHolder {

    const val SPLASH_SCREEN = "/login/SplashScreenActivity"
    const val LOGIN = "/login/LoginActivity"
    const val ONE_KEY_LOGIN = "/login/OneKeyLoginActivity"
    const val LOGIN_BEFORE = "/login/LoginBeforeActivity"
    const val BIND_MOBILE = "/login/BindMobileActivity"

    const val MAIN = "/main/MainActivity"

    const val WE_CHAT_BUSINESS = "/home/WechatBusinessActivity"

    //0元领好货
    const val ZERO_HOME = "/zero/ZeroGetGoodsHomeActivity"
    const val ZERO_INVITATION_DETAIL = "/zero/InvitationDetailActivity"
    const val BE_INVITED_ZERO = "/zero/BeInvitedZeroActivity"

    //秒杀
    const val SECKILL_HOME = "/seckill/SeckillHomeActivity"

    //新人免费送
    const val NEW_PEOPLE = "/new_people/NewPeopleActivity"
    const val NEW_PEOPLE_GOODS_DETAIL = "/new_people/NewPeopleGoodsDetailActivity"

    //拼团
    const val PIN_ORDER_BE_INVITED = "/pin/BeInvitedPinOrderActivity"

    //砍价
    const val BARGAIN_HOME = "/bargain/BargainActivity"

    const val PERSON_INFO = "/mine/PersonInfoActivity"
    const val PERSON_INFO_MODIFY = "/mine/PersonInfoChangeActivity"
    const val MESSAGE = "/mine/MessageActivity"
    const val MESSAGE_DETAIL = "/mine/MessageDetailActivity"
    const val EVALUATE = "/mine/EvaluateActivity"
    const val EVALUATE_APPLY = "/mine/EvaluateApplyActivity"
    const val RETURN_AND_EXCHANGE = "/mine/ReturnAndExchangeActivity"
    const val HELP_CENTER = "/mine/HelpCenterActivity"
    const val NOVICE_COURSE = "/mine/NoviceCourseActivity"
    const val MY_COLLECTION = "/mine/MyCollectionActivity"
    const val BROWSING_HISTORY = "/mine/BrowsingHistoryActivity"

    const val AUTHENTICATION = "/authentication/AuthenticationActivity"
    const val AUTHENTICATION_COMPARISON = "/authentication/AuthenticationComparisonActivity"
    const val AUTHENTICATIONED = "/authentication/AuthenticationedActivity"
    const val AUTHENTICATION_RECORD_VIDEO = "/authentication/RecordVideoActivity"

    const val GOODS_LIST = "/goods/GoodsListActivity"
    const val GOODS_DETAIL = "/goods/GoodsDetailActivity"
    const val GOODS_SEARCH_GOODS_STORE = "/goods/SearchGoodsStoreActivity"
    const val GOODS_SEARCH_GOODS = "/goods/SearchGoodsListActivity"
    const val GOODS_SEARCH_STORE = "/goods/SearchShopListActivity"
    const val GOODS_DISTRIBUTION_GOODS = "/goods/DistributionGoodsActivity" //商品分销

    const val SHOP_LIST = "/shop/ShopListActivity"
    const val SHOP_DETAIL = "/shop/ShopDetailActivity"
    const val ABOUT_SHOP = "/shop/AboutShopActivity"
    const val SHOP_DETAIL_GOODS_LIST = "/shop/ShopDetailGoodsListActivity"

    const val CONFIRM = "/confirm/ConfirmActivity"
    const val SELF_GET_POINT = "/confirm/SelfBuyPointActivity"
    const val CONFIRM_SEND_TYPE = "/confirm/SendTypeActivity"
    const val SELF_GET_MAP = "/confirm/SelfGetMapActivity"

    const val ORDER = "/mine/OrderActivity"
    const val ORDER_DELIVER_GOODS = "/order/DeliverGoodsActivity"
    const val ORDER_RECYCLER = "/order/OrderRecyclerActivity"
    const val ORDER_SEARCH = "/order/SearchOrderActivity"
    const val ORDER_DETAIL = "/order/OrderDetailActivity"
    const val ORDER_DEBT_PREPAY_DETAIL = "/order/OrderDebtPrepayDetailActivity"
    const val ORDER_REFUND_LIST = "/order/OrderRefundActivity" //申请退款列表
    const val ORDER_REFUND_APPLY = "/order/RefundApplyActivity" //申请订单退款
    const val ORDER_CHOOSE_GOODS = "/order/OrderChooseGoodsActivity" //批量选择商品
    const val ORDER_REFUND_CONSULT_HISTORY = "/order/ConsultHistoryActivity" //协商历史
    const val ORDER_REFUND_INPUT_SHIPPING = "/order/InputShippingActivity" //填写物流
    const val ORDER_REFUND_DETAIL = "/order/RefundDetailActivity" //退款详情
    const val ORDER_REFUND_PROGRESS = "/order/OrderRefundProgressActivity" //订单退款进度
    const val ORDER_TRANSACTIONSNAP = "/order/OrderTransactionSnapActivity"
    const val ORDER_TRANSACTIONSNAP_DETAIL = "/order/OrderTransactionSnapDetailActivity"

    /*--------------------------------余额--------------------------------*/
    const val BALANCE_USER = "/balance/BalanceUserActivity"
    const val BALANCE_STORE = "/balance/BalanceStoreActivity"
    const val BALANCE_ACCOUNT = "/balance/AccountActivity"
    const val BALANCE_ACCOUNT_ADD = "/balance/AccountAddActivity"
    const val BALANCE_ACCOUNT_ADD_ZFB = "/balance/AccountAddZFBActivity"

    /*--------------------------------支付--------------------------------*/
    const val PAY_ORDER_PAGE = "/order/PayOrderPageActivity"
    const val PAY_CREDIT_PREPAYORDER_DETAIL = "/order/CreditPrepayDetailActivity"
    const val PAY_ORDER_SUCCESS = "/order/PayOrderSuccessActivity"
    const val PAY_PIN_TUAN_ORDER_SUCCESS = "/order/PayPinTuanOrderSuccessActivity"


    const val SHOP_APPLY = "/shop/ApplyShopActivity" //申请店铺

    /*--------------------------------店铺--------------------------------*/
    const val STORE_ROOT = "/store/mode_store/StoreActivity"
    const val STORE_ADD_GOODS = "/store/mode_store/AddGoodsActivity"
    const val STORE_SETTING = "/store/mode_store/StoreSettingActivity"
    const val STORE_SUGGEST = "/store/mode_store/SuggestActivity"
    const val STORE_VIP_LIST = "/store/mode_store/VIPListActivity" //客户VIP列表
    const val STORE_GOODS_THREE_CATEGORY = "/store/mode_store/ThreeCategoryActivity"
    const val STORE_GOODS_SPEC_EDIT = "/store/mode_store/SpecEditActivity"
    const val STORE_SEND_GOODS_DETAIL = "/store/mode_store/StoreSendGoodsDetailActivity"
    const val STORE_CHANGE_SEND_GOODS = "/store/mode_store/StoreChangeSendGoodsActivity"
    const val STORE_SEND_GOODS_PROCESS = "/store/mode_store/StoreSendGoodsProcessActivity"
    const val STORE_SEND_GOODS = "/store/mode_store/StoreSendGoodsActivity"
    const val STORE_ORDER_CHOOSE_GOODS = "/store/OrderChooseGoodsActivity" //选择商品
    const val STORE_DEBT_PREPAY_ORDER_CHOOSE_GOODS = "/store/DebtPrepayOrderChooseGoodsActivity" //选择商品
    const val STORE_CHANGE_PRICE = "/store/mode_store/StoreChangePriceActivity"
    const val STORE_DEBT_PREPAY_CHANGE_PRICE = "/store/mode_store/DebtPrepayStoreChangePriceActivity"

    const val STORE_ORDER_AFTER_SALES_DETAIL = "/store/mode_store/StoreOrderAfterSaleDetailActivity"

    /*--------------------------------店铺订单--------------------------------*/
    const val STORE_ORDER_DETAIL = "/store/order/StoreOrderDetailActivity"
    const val STORE_ORDER_SEARCH = "/store/order/SearchStoreOrderActivity"

    /*--------------------------------供应商--------------------------------*/
    const val SUPPLIER_LIST = "/supplier/SupplierListActivity"
    const val SUPPLIER_SEARCH_LIST = "/supplier/SupplierSearchListActivity"
    const val SUPPLIER_DEBT_PREPAY_LIST = "/supplier/SupplierDebtPrepayActivity" //赊账订单列表
    const val SUPPLIER_REFUND_INFO_LIST = "/supplier/SupplierRefundInfoActivity" //供应商退款信息列表
    const val SUPPLIER_REFUND_INFO_DETAIL = "/supplier/SupplierRefundInfoDetailActivity" //供应商退款信息详情
    const val SUPPLIER_PRIVATE_LIST = "/supplier/SupplierPrivateActivity" //私密商家审核列表
    const val SUPPLIER_ARREARS_LIST = "/supplier/ArrearsListActivity" //欠款单
    const val SUPPLIER_DEBT_REVIEW_DETAIL = "/supplier/DebtReviewDetailActivity" //欠款单审核
    const val SUPPLIER_PRIVATE_DETAIL_DETAIL = "/supplier/SupplierPrivateDetailActivity" //私密商品详情
    const val SUPPLIER_SETTLEMENT_DETAIL = "/supplier/SupplierSettlementDetailActivity" //付款、退款信息详情
    const val SUPPLIER_ADD_EDIT = "/supplier/SupplierAddEditActivity"
    const val SUPPLIER_CHOOSE = "/supplier/SupplierChooseActivity"
    const val SUPPLIER_DETAIL = "/supplier/SupplierDetailActivity"
    const val SUPPLIER_HOME = "/supplier/SupplierHomeActivity"

    /*--------------------------------客户--------------------------------*/
    const val CUSTOMER_LIST = "/customer/CustomerListActivity"
    const val CUSTOMER_SEARCH_LIST = "/customer/CustomerSearchListActivity"
    const val CUSTOMER_LIST_SAMPLE = "/customer/CustomerListSampleActivity"
    const val CUSTOMER_ADD_EDIT = "/customer/CustomerAddEditActivity"
    const val CUSTOMER_MAKE_ARREARS = "/customer/MakeArrearsActivity"
    const val CUSTOMER_DEBT_CERTIFICATE = "/customer/DebtCertificateActivity"
    const val CUSTOMER_PAY_INFO = "/customer/CustomerPayInfoActivity"
    const val CUSTOMER_PAY_INFO_DETAIL = "/customer/CustomerPayInfoDetailActivity"
    const val CUSTOMER_DEBT_PREPAY_LIST = "/customer/CustomerDebtPrepayActivity"
    const val CUSTOMER_DEBT_PREPAY_DETAIL = "/customer/CustomerDebtPrepayDetailActivity"
    const val CUSTOMER_PRIVATE_LIST = "/customer/CustomerPrivateActivity"
    const val CUSTOMER_PRIVATE_DETAIL = "/customer/CustomerPrivateDetailActivity"
    const val CUSTOMER_ARREARS_LIST = "/customer/CustomerArrearsListActivity" //欠款单列表
    const val CUSTOMER_ARREARS_DETAIL = "/customer/CustomerDebtReviewDetailActivity" //欠款单详情
    const val CUSTOMER_CHOOSE = "/customer/CustomerChooseActivity"
    const val CUSTOMER_APPLY_LIST = "/customer/CustomerApplyListActivity"
    const val CUSTOMER_REFUND_INFO_DETAIL = "/customer/CustomerRefundInfoDetailActivity" //退款详情
    const val CUSTOMER_HOME = "/customer/CustomerHomeActivity"


    const val MO_LIN_INFO_DETAIL = "/store/MoLinInfoDetailActivity" //抹零信息详情

    /*-------------------------------- 地址管理--------------------------------*/
    const val COMBINATION_ADDRESS = "/address/CombinationAddressActivity"
    const val ADDRESS_MANAGER = "/address/AddressManagerActivity"
    const val ADD_OR_EDIT_ADDRESS = "/address/AddOrEditAddressActivity"
    const val PACKAGE_ADDRESS = "/address/PackageAddressActivity"
    const val ADD_OR_EDIT_PACKAGE_ADDRESS = "/address/AddOrEditPackageAddressActivity"
    const val STORE_CHANGE_ADDRESS = "/address/StoreChangeAddressActivity"
    const val STORE_SELF_GET_POINT = "/address/StoreSelfGetPointActivity"

    /*-------------------------------- 物流详情--------------------------------*/
    const val SHIPPING_DETAIL = "/shipping/ShippingDetailActivity"

    /*-------------------------------- 测试--------------------------------*/
    const val STORE_NUMBER_1_MAIN = "/number1/Number1MainActivity"

    /*-------------------------------- 测试--------------------------------*/
    const val TEST = "/test/TESTActivity"


    val activityArray = ArrayList<Activity>()
    val payOrderActivityArray = ArrayList<Activity>() //管理支付的Activity的集合

    fun clearPayOrderActivityArray() {
        payOrderActivityArray.forEach { it.finish() }
        payOrderActivityArray.clear()
    }

    @JvmStatic
    fun containsActivity(className: String): Boolean {
        activityArray.forEach {
            if (it::class.java.simpleName == className) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun addActivity(context: Context) {
        activityArray.add(context as Activity)
    }

    @JvmStatic
    fun removeActivity(context: Context) {
        activityArray.remove(context as Activity)
    }

    @JvmStatic
    fun removeActivityForClassName(className: String) {
        activityArray.forEachIndexed { index, activity ->
            if (activity.localClassName == className) {
                activity.finish()
                activityArray.removeAt(index)
                return@forEachIndexed
            }
        }
    }

    fun removeAllActivity() {
        for (activity in activityArray) {
            activity.finish()
        }
    }
}