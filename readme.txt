需求拆解：
1. 启动时，加载payment信息的txt
2. txt包含所有currency amount记录
3. currency都是3位大写字母
4. 有接口可以输入并且提交 currency amount记录
5. 每分钟一轮控制台输出币种净值
6. 币种的净值为0时，不在console的循环显示中显示
7. 用户输入“quit”,程序退出（这里需求没有太理解好，程序退出是整个server都停止还是页面效果的退出)
8. 用muserver框架写
9. 接口1：请求提交currency amount
10.接口2：(1)提交currency查币种最终合计后金额
         (2)提交currency查币种所有有关的记录
11.检查currency/amount是否有效，无效时输出错误信息
12.写readme
13.junit（不太熟悉muServer，所以没有有效的junit）
14.使用线程是，要线程安全

完成程度：
第7点没完成，原因是不太理解需求，而且没弄懂怎么在handler里面调用ApplicationEntry.java类中初始化的MuServer对象的stop()方法
第13点没完成，原因是对MuServer的调用机制不太理解，但junit调用handler的handle方法时，暂时未成功初始化好MuRequest、MuResponse对象
除了这两点，其他需求点均已完成

demo结构：
1. RunTestApplication.java    : 在test/java文件夹下，作用是本地启动demo应用时候的入口
2. ApplicationEntry.java      : 在main/java文件夹下，作用是配置及初始化mu server服务，配置接口路径及关联处理逻辑的类
3. PaymentHandler.java        : 在main/java文件夹下，作用是根据请求参数的currencyCode和amount参数值,把这两个参数的值按照文档要求保存到payment信息的txt文本中且返回payment number，或当请求数据有问题的时候返回错误信息
4. PaymentEventHandler.java   : 在main/java文件夹下，作用是根据请求参数的currencyCode参数值，查询出该币种相关的所有插入到payment信息txt文本的记录且返回出去
5. LastestPaymentHandler.java : 在main/java文件夹下，作用是根据请求参数的currencyCode参数值，查询出该币种相关的所有插入到payment信息txt文本的记录，并对amount数字求和且返回出去
6. TimeResource.java          : 在main/java文件夹下，作用是定时每分钟把payment信息文本中的各个币种的amount值求和，并且输出到控制台
7. FileManager.java           : 在main/java文件夹下，里面包含了读取和写入payment信息文本的功能函数
8. Constants.java             : 在main/java文件夹下，用来存放产量
9. Utils.java                 : 在main/java文件夹下，用来存放多个类重复利用的函数
10.ResponseMessage.java       : 在main/java文件夹下，是返回信息的pojo类
10.log4j.properties           : 在main/resource文件夹下，用来配置log4j输出样式
11 paymentInfo.txt            : payment信息文本，系统自动生成，会生成到target/test-classes文件夹下

请求方式例子：
1.Request/Response接口，接口名/checkPayment，用于提交currencyCode和amount
http://localhost:8088/checkPayment?currencyCode=CNY&amount=13

2.Server Sent Event接口，接口名/checkEvent，用于获取currencyCode对应的所有记录（因为不确定Server Sent Event接口需求是否只获取payment更新后amount的总额，所以写了这一条api）
http://localhost:8088/checkEvent?currencyCode=CNY

3.Server Sent Event接口，接口名/checkLastestPayment，用于获取currencyCode对应的币种的amount总和
http://localhost:8088/checkLastestPayment?currencyCode=GBP


返回数据列举：
1. 当Request/Response接口时
    （1）GET请求中不包含currencyCode时，返回信息的‘responseMessage’字段值会显示“input currency code invalid”
    （2）GET请求中currencyCode的值不为有效的币种时，返回信息的‘responseMessage’字段值会显示“input currency code invalid”
    （3）GET请求中不包含amount时，返回信息的‘responseMessage’字段值会显示“input amount invalid”
    （4）GET请求中amount的值不为有效的数字（包含正、负数）时，返回信息的‘responseMessage’字段值会显示“input amount invalid”
        以上4种的情况返回例子如下：
        example: {'responseCode':400, 'responseMessage':'input amount invalid', 'responseTimeStamp':'202208051128272'}
                 {'responseCode':400, 'responseMessage':'input currency code invalid', 'responseTimeStamp':'20220805112835166'}
    （5）GET请求中包含currencyCode和amount，且值都是正确的情况下，会返回包含如下结果
        example: {'responseCode':200, 'responseMessage':'the payment number is :2022080511260837514', 'responseTimeStamp':'20220805112608375'}

2. Server Sent Event接口，接口名/checkEvent
    （1）GET请求中不包含currencyCode时，返回信息的‘responseMessage’字段值会显示“input currency code invalid”
    （2）GET请求中currencyCode的值不为有效的币种时，返回信息的‘responseMessage’字段值会显示“input currency code invalid”
        以上2种的情况返回例子如下：
        example: {'responseCode':400, 'responseMessage':'input currency code invalid', 'responseTimeStamp':'20220805112904937'}
    （3）GET请求中包含currencyCode且值为有效的币种时，会返回包含如下结果
        example:  {'responseCode':200, 'responseMessage':'USD  1234
                  USD  1234
                  USD  2
                  USD  23
                  USD  -10
                  USD  -10
                  USD  -10
                  USD  -10
                  USD  -10
                  USD  -10
                  USD  0
                  USD  0', 'responseTimeStamp':'20220805113332712'}、

3. Server Sent Event接口，接口名/checkLastestPayment
    （1）GET请求中不包含currencyCode时，返回信息的‘responseMessage’字段值会显示“input currency code invalid”
    （2）GET请求中currencyCode的值不为有效的币种时，返回信息的‘responseMessage’字段值会显示“input currency code invalid”
        以上2种的情况返回例子如下：
        example: {'responseCode':400, 'responseMessage':'input currency code invalid', 'responseTimeStamp':'20220805112904937'}
    （3）GET请求中包含currencyCode且值为有效的币种时，会返回包含如下结果
        example:  {'responseCode':200, 'responseMessage':'USD  2433.0', 'responseTimeStamp':'20220805113415753'}