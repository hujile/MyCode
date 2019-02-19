public class LambdaExpress {
//使用lambda表达式时，接口中有且只能有一个方法，若需要有多个方法，
//可使用default声明方法，或者static静态方法
    public static void main(String[] args) {
        Foo foo = new Foo() {
            @Override
            public void sayHello() {
                System.out.println("You never find yourself until you face to the truth.\n\n\n");
            }
        };
        foo.sayHello();
        System.out.println("--------------------这是一道华丽分割线---------------------");
        //lambda表达式写法
        Zoo zoo =(a,b) -> {
            System.out.println("Welcome , bro ~ ~ ~");
            return a+b;
        };
        System.out.println("a和b的和为："+zoo.add(1,1));
        System.out.println("a和b的商为："+zoo.div(2,2));
        System.out.println("a和b的积为："+Zoo.mul(1,1));
    }
}
@FunctionalInterface
//此注解声明函数式接口，而函数式接口可以使用lambda表达式表示，
interface Foo{
    public void sayHello();
}
interface Zoo{
    public int add(int a,int b);
    public default  int div(int a,int b){
        return a/b;
    }
    public static int mul(int a,int b){
        return a*b;
    }
}

