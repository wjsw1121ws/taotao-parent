package com.wcc.freemarker;

import com.wcc.pojo.Person;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * @Description: freemarker生成静态页面
 * @ClassName: Genhtml
 * @Auther: changchun_wu
 * @Date: 2019/2/23 13:49
 * @Version: 1.0
 **/
public class Genhtml {
    private static final String DIRECTORY_PATH =
            "F:\\IDEA\\taotao-parent\\taotao-study\\freemarker\\src\\test\\resources\\template";

    /**
     * 生成静态方法
     */
    @Test
    public void testFreemarker() throws IOException, TemplateException {
        //模板 + 数据  -->静态页面
        //1.创建configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //2.创建模板所在的路径  可以是任意目录
        configuration.setDirectoryForTemplateLoading(new File(DIRECTORY_PATH));
        //3.设置字符编码集
        configuration.setDefaultEncoding("utf-8");
        //4.创建一个模板文件    官方提供的是.ftl,任意的都行
        //5.加载模板文件 获取模板文件 相对路径的文件名
        Template template = configuration.getTemplate("template.ftl");
        //6.创建数据集 可以使用map或者pojo
        Map map = new HashMap();
        //测试基本数据类型
        map.put("hello","include标签的使用");

        //测试pojo
        Person person1 = new Person(1000l,"曹操");
        Person person2 = new Person(2000l,"刘备");
        Person person3 = new Person(3000l,"孙权");
        map.put("person1",person1);
        map.put("person2",person2);
        map.put("person3",person3);

        //测试list集合
        List<Person> list = new ArrayList<Person>();
        list.add(person1);
        list.add(person2);
        list.add(person2);

        //测试map集合
        Map map1 = new HashMap();
        map1.put("p1",new Person(1l,"大乔"));
        map1.put("p2",new Person(2l,"孙权"));
        map1.put("p3",new Person(3l,"小乔"));
        map1.put("p4",new Person(4l,"周瑜"));
        map.put("map",map1);
        map.put("list",list);

        //测试日期
        map.put("date",new Date());

        //null值
        map.put("keynull",null);
        //7.创建writer输出了
        Writer writer = new FileWriter("D:\\Freemarker\\template.html");
        //8.通过调用模板中的process方法输出
        template.process(map,writer);
        //9.关闭流
        writer.close();
    }
}
