package com.facishare.openapi.common;

/**
 * Created by sunsk on 2017/8/7.
 */
public interface workFlows {
    //文件Business字典
    public static enum  WorkFlowscope{
        客户("AccountObj"),
        销售订单("SalesOrderObj"),
        销售线索("LeadsObj"),
        联系人("ContactObj"),
        回款("PaymentObj"),
        公海("HighSeasObj"),
        合同("ContractObj"),
        退货单("ReturnedGoodsInvoiceObj"),
        开票申请("InvoiceApplicationObj"),
        退款("RefundObj"),
        销售流程("SaleActionObj");


        private final String text;
        private WorkFlowscope(final String text){
            this.text=text;
        }

        public String toString(){
            return text;
        }

    }
    public static enum  wordNum{
        二十一("我的流程我的流程我的流程我的流程我的流程1","name"),
        NAMENULL("","name"),
        IDnull("","sourceWorkflowId"),
        DSnull("","description"),
        一百一("w0123456789012345678901234567890123456789012345678901234567890123456789012345678901234" +
                "567890123456789","sourceWorkflowId"),
        五百("这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是" +
                "五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这" +
                "是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字" +
                "这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百" +
                "字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百" +
                "字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百" +
                "字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百" +
                "字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百" +
                "字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百" +
                "字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百字这是五百" +
                "字这是五百字1","description");


        private final String text;
        private  String index;
        private wordNum(final String text,String index){
            this.text=text;
            this.index=index;
        }

        public String toString(){
            return text;
        }
        public String toName(){
            return index;
        }

    }
}
