UML�еĹ�ϵ
	������ϵ  .........>
	����   ----------
	�������̳��� ---1>
	ʵ�֣�ʵ�ֽӿ� ...1>
	

�������ԡ�Head First ���ģʽ��
����ģʽ��������һ����������Ľӿڣ������������Ҫʵ������������һ�������̷��������ʵ�����Ƴٵ����ࡣ
���ȣ�
���ǿ���һ��pizza�꣬��pizza��
�������һ��pizza��
public class Pizza {
    public void prepare(){};
    public void bake(){};
    public void cut(){};
    public void box(){};
}
Ȼ����pizza����
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
���ǣ�����Ҫ���಻ͬ�����pizza��ʱ�򣬷���Ŀǰ���޷�����ġ�������Ҫ��
�������Ǽ�����������pizza��CheesePizza��GreekPizza��PepperoniPizza����������̳�pizza
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
����type������ͬ��pizza
���磺����ʱ������Ӷ�������pizza����Ҫɾ����pizza���㷢�֣���ֻ���޸�PizzaStore��orderPizza()����ʵ�֣������������Ŀ���ԭ�������޸Ĺرգ�������չ���š����������ֲ��õ���ƣ�
�������Ƿ��֣�����Ҫ�޸ĵ�ֻ�Ǵ���pizzaʵ���Ĳ��֣�����pizza�����̲��趼��һ���Ĳ���Ҫ�ı䣬��ô���ǻ��뵽�ѱ仯�Ĳ��ֺͲ��仯�Ĳ��ָ��뿪������ר�Ž�����pizza�Ĳ���ӱ������������ŵ�����һ����ȥʵ�֡�������µ��࣬���Ǿͽ���һ����������������������ϸ�ڡ�SimplePizzaFactory�ࡣ
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
������һ��createPizza()���������пͻ������������ʵ�������󡣸о����⻹��û���������Ҫ�޸Ĵ��롣����Ŀǰ����ʽ�����ĺô����ǿ����ж���ͻ�������PizzaShop.
PizzaStoreҲҪ�޸ģ�
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
����Ǽ򵥹������򵥹�������һ�����ģʽ�������Ƚ���һ�ֱ��ϰ�ߡ�


�������ڣ���������������ˡ����Ǽ��˵������Ϊ����ͬ��Ҫ�ṩ��ͬ��ζ�ı������һ��в�ͬ����������ͬ�ļӹ�����ʱ�����Ҫ���Storeȥ������ͬ�Ķ���

��PizzaStore��Ƴɳ���
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
��������Store��NYPizzaStore��ChicagoPizzaStore��CaliforniaPizzaStore�����̳�PizzaStore����ʱPizzaStore���ǹ�����
���ִ�����ʽ�������������Լ���������
�������������������Ĵ�����������������Ϊ��װ�������С������ͻ������й��ڳ���Ĵ����������󴴽���������ˡ�
������һ��NYPizzaStore���������������Լ����е�NYStyleCheesePizza��NYStyleVeggiePizza��NYStyleClamPizza��NYStylePepperoniPizza
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
��ô��pizza������NYStyleCheesePizza����
public class NYStyleCheesePizza extends Pizza {
    public NYStyleCheesePizza() {
        name = "NY Style Sauce and Cheese Pizza";
        dough = "Thin Crust Dough";//�����
        sauce = "Marinara Sauce";//���ѽ�
        toppings.add("Grated Reggiano Cheese");//�߼�����
    }
    void cut(){
        System.out.println("Cutting the pizza into Square slices");//�г�������
    }
}
�Լ����Լ���ʵ�ֺ�������������д�˸����cut��
��������
public class PizzaTestDriver {
    public static void main(String[] args) {
        PizzaStore nyStore = new NYPizzaStore();
        Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println(String.format("Ethan ordered a %S",pizza.getName()));
    }
}
���н����
PreparingNY Style Sauce and Cheese Pizza
Tossing dough...
Adding sauce...
Adding toppings
 Grated Reggiano Cheese
Bake for 25 minutes at 350
Cutting the pizza into Square slices
Place pizza inofficial PizzaStore box
Ethan ordered a NY STYLE SAUCE AND CHEESE PIZZA
�������������̾��ǣ�����pizzastore��ͨ����̬������NyPizzaStoreʵ��������pizzaStore��orderPizza��������ΪorderPizza�Ǹ���PizzaOrder�еķ�����������ֱ�Ӿ͵����˸����orderPizza()���������orderPizza�����˳��󷽷�createPizza�����ó��󷽷������඼ʵ���ˣ���ֱ�Ӿ͵�����NYPizzaStore()�е�createPizza������
����ǹ�������ģʽ������ģʽͨ�����������Ҫ�����Ķ�����ʲô�����ﵽ�����󴴽��Ĺ��̷�װ��Ŀ�ġ�
�������������ԭ���ϣ���ÿ��pizza���������ṩ�Ĳ��ϡ���Pizza ��prapare()�����ĳɳ���ģ������������ǵ�ԭ����
���ԭ���Ϲ�����
public interface PizzaIngredientFactory {
    Dough createDough();
    Sauce createSauce();
    Cheese createCheese();
    Veggies[] createVeggies();
    Pepperoni createPepperoni();
    Clams createClam();
}
�����Լ���ԭ���Ϲ���
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
        Veggies veggies[] = {new Veggies("������"),new Veggies("����"),new Veggies("�ײ�")};
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
    String name;//����
    Dough dough;//��������
    Sauce sauce;//��������
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
�����޸�pizza��
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
Ŀǰ��Pizza������صĹ�������ԭ�ϣ���������ԭ��������ʹ�õĹ�����Pizza�������������Щԭ�ϣ���ֻ֪���������pizza������Pizza������ԭ��֮�䱻���
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
������pizza��
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
���Ͼͳ��󹤳������ó��󷽷���Ҫ��������д��ͬʱ�������崴�����󽻸�����Ҫ��ɡ�




�򵥹������򵥹�������һ�����ģʽ�������Ƚ���һ�ֱ��ϰ�ߡ�
����ģʽ��������һ����������Ľӿڣ������������Ҫʵ������������һ�������̷��������ʵ�����Ƴٵ����ࡣ
���󹤳�ģʽ���ṩһ���ӿڣ����ڴ�����ػ���������ļ��壬������Ҫ��ȷָ�������ࡣ
��̬�������ѹ��������һ����̬�ķ���������Ҫʹ�ô������󷢷�����ʵ��������ȱ����ǲ���ͨ���̳����ı䴴����������Ϊ��


