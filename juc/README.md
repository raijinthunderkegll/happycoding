### java.util.concurrent包创始人：道格·利 DOUG lEA  


##### 用户线程
- 系统的工作线程
- deamon = false 
- setdeamon(true)可以将用户线程修改为守护线程，但要在start()方法之前调用

##### 守护线程
- 为其他线程提供服务的，如果没有用户线程，只有守护线程，虚拟机停止运行
- 垃圾回收线程
- deamon = true

### CompletableFuture
- Future可以为主线程开一个分支任务，专门处理耗时费力的复杂业务
- 主线程可以调用接口获取子线程状态、中断子线程等操作 
- FutureTask实现Future接口
- FutureTask.get()导致程序阻塞，得先用isDone()轮询，但会空转消耗系统资源