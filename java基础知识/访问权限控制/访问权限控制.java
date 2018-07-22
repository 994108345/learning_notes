从最大的权限到最小的权限：public、protected、包访问权限（没有关键字）、private
1 包：库单元
	package和import关键字允许你做的，是将单一的全局名字空间分隔开。是的无论多少人使用Internet已经java开始编写类。都不会出现名称冲突的情况。
	按照惯例。package名称的第一部分是类的创建者的反顺序的Internet域名。因为域名是独一无二的，所以package的名称也是独一无二的。
	
	public ：哪里都可以引用
	不加权限修饰符：同一个包里可以用
	protected：只有继承该类的类可以引用
	private：只有自己本类可以引用
	每个编译单元只能有一个public类。