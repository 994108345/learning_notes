UML中的关系
	依赖关系  .........>
	关联   ----------
	泛化：继承类 ---1>
	实现：实现接口 ...1>
	

本例来自《Head First 设计模式》
工厂模式：定义了一个创建对象的接口，但由子类决定要实例化的类是哪一个。工程方法让类把实例化推迟到子类。
首先：
我们开了一个pizza店，卖pizza。
首先设计一个pizza类
public class Pizza {
    public void prepare(){};
    public void bake(){};
    public void cut(){};
    public void box(){};
}
然后是pizza店类
public class PizzaStore {
    public Pizza orderPizza(){
        Pizza pizza = new Pizza();
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
这是，我想要更多不同种类的pizza的时候，发现目前的无法满足的。所以需要改
现在我们加入三种其他pizza：CheesePizza、GreekPizza、PepperoniPizza。这三个类继承pizza
public class PepperoniPizza extends Pizza {
}
public class GreekPizza extends Pizza  {
}
public class CheesePizza extends Pizza  {
}
public class PizzaStore {
    public Pizza orderPizza(String type){
        Pizza pizza = null;
        if(type.equals("cheese")){
            pizza = new CheesePizza();
        }else if(type.equals("greek")){
            pizza = new GreekPizza();
        }else if (type.equals("pepperoni")){
            pizza = new PepperoniPizza();
        }
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
根据type创建不同的pizza
假如：你这时候还想添加多个种类的pizza，想要删除掉pizza，你发现，你只能修改PizzaStore的orderPizza()方法实现！根据面向对象的开闭原则，面向修改关闭，面向扩展开放。所以这是种不好的设计！
现在我们发现，我们要修改的只是创建pizza实例的部分，下面pizza的流程步骤都是一样的不需要改变，那么我们会想到把变化的部分和不变化的部分隔离开来，及专门将创建pizza的步骤从本类抽离出来，放到另外一个类去实现。而这个新的类，我们就叫做一个工厂，工厂处理创建对象细节。SimplePizzaFactory类。
public class SimplePizzaFactory {
    public Pizza createPizza(String type){
        Pizza pizza = null;
        if(type.equals("cheese")){
            pizza = new CheesePizza();
        }else if(type.equals("greek")){
            pizza = new GreekPizza();
        }else if (type.equals("pepperoni")){
            pizza = new PepperoniPizza();
        }
        return pizza;
    }
}
定义了一个createPizza()方法，所有客户用这个方法来实例化对象。感觉问题还是没解决还是需要修改代码。但是目前的形式，最大的好处就是可以有多个客户，比如PizzaShop.
PizzaStore也要修改：
public class PizzaStore {
    SimplePizzaFactory factory;

    public PizzaStore(SimplePizzaFactory factory) {
        this.factory = factory;
    }
    public Pizza orderPizza(String type){
        Pizza pizza = factory.createPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
这就是简单工厂，简单工厂不是一个设计模式，反而比较像一种编程习惯。


假如现在，有其他店铺想加盟。但是加盟店可能因为地域不同而要提供不同风味的比萨，且还有不同的做法，不同的加工。这时候就需要多个Store去创建不同的对象。

将PizzaStore设计成超类
public abstract class PizzaStore {
    public Pizza orderPizza(String type){
        Pizza pizza = createPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
    abstract Pizza createPizza(String type);
}
创建三种Store：NYPizzaStore、ChicagoPizzaStore、CaliforniaPizzaStore。都继承PizzaStore。此时PizzaStore就是工厂。
这种创建方式就是允许子类自己做决定！
工厂方法用来处理对象的创建，并将这样的行为封装在子类中。这样客户程序中关于超类的代码和子类对象创建代码解耦了。
我们拿一个NYPizzaStore来举例，他创建自己独有的NYStyleCheesePizza、NYStyleVeggiePizza、NYStyleClamPizza、NYStylePepperoniPizza
public class NYPizzaStore extends  PizzaStore{
    @Override
    Pizza createPizza(String type) {
        if(type.equals("cheese")){
            return new NYStyleCheesePizza();
        }else if(type.equals("veggie")){
            return new NYStyleVeggiePizza();
        }else if (type.equals("clam")){
            return new NYStyleClamPizza();
        }else if (type.equals("pepperoni")){
            return new NYStylePepperoniPizza();
        }else return null;
    }
}
这么多pizza我们拿NYStyleCheesePizza举例
public class NYStyleCheesePizza extends Pizza {
    public NYStyleCheesePizza() {
        name = "NY Style Sauce and Cheese Pizza";
        dough = "Thin Crust Dough";//薄面饼
        sauce = "Marinara Sauce";//番茄酱
        toppings.add("Grated Reggiano Cheese");//高级奶酪
    }
    void cut(){
        System.out.println("Cutting the pizza into Square slices");//切成正方形
    }
}
自己有自己的实现和制作方法，重写了父类的cut！
最后测试类
public class PizzaTestDriver {
    public static void main(String[] args) {
        PizzaStore nyStore = new NYPizzaStore();
        Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println(String.format("Ethan ordered a %S",pizza.getName()));
    }
}
运行结果：
PreparingNY Style Sauce and Cheese Pizza
Tossing dough...
Adding sauce...
Adding toppings
 Grated Reggiano Cheese
Bake for 25 minutes at 350
Cutting the pizza into Square slices
Place pizza inofficial PizzaStore box
Ethan ordered a NY STYLE SAUCE AND CHEESE PIZZA
整个的运行流程就是，创建pizzastore，通过多态，创建NyPizzaStore实例，引用pizzaStore的orderPizza方法，因为orderPizza是父类PizzaOrder中的方法，所以是直接就调用了父类的orderPizza()，而父类的orderPizza调用了抽象方法createPizza，而该抽象方法被子类都实现了，即直接就调用了NYPizzaStore()中的createPizza方法。
这就是工厂方法模式。工厂模式通过让子类决定要创建的对象是什么，来达到将对象创建的过程封装的目的。
现在我们想控制原材料，让每个pizza都用我们提供的材料。把Pizza 的prapare()方法改成抽象的，，并加上我们的原材料
设计原材料工厂：
public interface PizzaIngredientFactory {
    Dough createDough();
    Sauce createSauce();
    Cheese createCheese();
    Veggies[] createVeggies();
    Pepperoni createPepperoni();
    Clams createClam();
}
各地自己的原材料工厂
public class NYPizzaIngreDientFactory implements  PizzaIngredientFactory {
    @Override
    public Dough createDough() {
        return new ThinCrustDough();
    }

    @Override
    public Sauce createSauce() {
        return new MarinaraSauce();
    }

    @Override
    public Cheese createCheese() {
        return new ReggianoCheese();
    }

    @Override
    public Veggies[] createVeggies() {
        Veggies veggies[] = {new Veggies("西红柿"),new Veggies("生菜"),new Veggies("白菜")};
        return veggies;
    }

    @Override
    public Pepperoni createPepperoni() {
        return new SlicedPepperoni();
    }

    @Override
    public Clams createClam() {
        return new FreshClams();
    }
}

public abstract class Pizza {
    String name;//名称
    Dough dough;//面团类型
    Sauce sauce;//酱料类型
    Veggies veggies[];
    Cheese cheese;
    Pepperoni pepperoni;
    Clams clam;
    abstract void prepare();
    void bake(){
        System.out.println("Bake for 25 minutes at 350");
    };
    void cut(){
        System.out.println("Cutting the pizza into diagonal slices");
    };
    void box(){
        System.out.println("Place pizza inofficial PizzaStore box");
    };
    public String getName(){
        return name;
    }
}
继续修改pizza类
public class CheesePizza extends Pizza  {
    PizzaIngredientFactory ingredientFactory;

    public CheesePizza(PizzaIngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    @Override
    void prepare() {
        System.out.println("Preparing " + name);
        dough = ingredientFactory.createDough();
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
    }
}
目前，Pizza利用相关的工厂生产原料，所生产的原料依赖所使用的工厂，Pizza类根本不关心这些原料，它只知道如何制作pizza。现在Pizza和区域原料之间被解耦。
public class ClamPizza extends Pizza{
    PizzaIngredientFactory ingredientFactory;

    public ClamPizza(PizzaIngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    @Override
    void prepare() {
        System.out.println("Preparing" + name);
        dough = ingredientFactory.createDough();
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
        clam = ingredientFactory.createClam();
    }
}
再来看pizza店
public class NYPizzaStore extends  PizzaStore{
    @Override
    Pizza createPizza(String type) {
        PizzaIngredientFactory ingredientFactory = new NYPizzaIngreDientFactory();
        Pizza pizza = null;
        if(type.equals("cheese")){
            pizza = new CheesePizza(ingredientFactory);
            pizza.setName("New Tork Style Cheese Pizza");
        }else if (type.equals("clam")){
            pizza = new CheesePizza(ingredientFactory);
            pizza.setName("New Tork Style Clam Pizza");
        }
        return pizza;
    }
}
以上就抽象工厂，利用抽象方法，要求子类重写。同时，将具体创建对象交给子类要完成。




简单工厂：简单工厂不是一个设计模式，反而比较像一种编程习惯。
工厂模式：定义了一个创建对象的接口，但由子类决定要实例化的类是哪一个。工程方法让类把实例化推迟到子类。
抽象工厂模式：提供一个接口，用于创建相关或依赖对象的家族，而不需要明确指定具体类。
静态工厂：把工厂定义成一个静态的方法。不需要使用创建对象发方法来实例化对象。缺点就是不能通过继承来改变创建方法的行为。


