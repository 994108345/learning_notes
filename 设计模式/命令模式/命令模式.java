命令模式：将一个请求封装为一个对象，从而使你可用不同的请求对客户进行参数化，对请求排队或记录请求日志，以及支持可撤销的操作。
命令模式的优点：
1.它能比较容易的设计一个命令队列，
2.在需要的情况下，可以比较容易地将命令记入日志。
3.允许接收请求的一方决定是否要否决请求。
4.可以容易的实现对请求的撤销和重做。
5.由于加进新的具体命令类不影响其他的类，因此增加新的具体命令类很容易。
6.命令模式把一个请求一个操作的对象与回到怎么执行一个操作的对象割开。
只有在真正需要撤销恢复等操作功能的时候，把原理的代码重构为命令模式才有意义。