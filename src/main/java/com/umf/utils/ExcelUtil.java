 package com.umf.utils;

 import com.alibaba.excel.EasyExcelFactory;
 import com.alibaba.excel.ExcelWriter;
 import com.alibaba.excel.context.AnalysisContext;
 import com.alibaba.excel.event.AnalysisEventListener;
 import com.alibaba.excel.metadata.BaseRowModel;
 import com.alibaba.excel.metadata.Sheet;
 import lombok.Data;
 import lombok.Getter;
 import lombok.Setter;
 import org.springframework.util.CollectionUtils;

 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.OutputStream;
 import java.util.*;

 /**
  * @Author:LiuYuKun
  * @Date:2019/12/17 17:04
  * @Description:导出Excel工具类
  * @Description:（传入List<Map<String,Object>>类型的数据时，必须传入LinkedHashMap<字段名，对应的中文>类型的表头，效率最慢）
  * @Description:（传入List<LinkedHashMap<String,Object>>和List<List<Object>>类型的数据时，传入任何格式表头都可以）
  * @Description:（传入List<List<Object>>格式的数据、和传入List<List<String>>类型的表头时，效率最高）
  *
  * @Description:表头格式：List<List<String>>、String[]、LinkedHashMap<String,String>
  * @Description:数据格式：List<List<Object>>、List<LinkedHashMap<String,Object>>、List<Map<String,Object>>
  *
  * @Description:springboot和mybatis同时使用的时候，数据为空时没有该字段，会出现数据不正确的问题，需要设置mybatis
  */
 public class ExcelUtil {

     private static Sheet initSheet;
     private static OutputStream outputStream;
     private static ExcelWriter writer;
     /*当前页数据的数量*/
     private static int currentNum = 0;
     /*单页最大数据的数量，Excel2003及以前版本65536行、256列；Excel2007、2010版本1048576行、16384列 */
     private static int maxNum = 49999;
     /*当数据有序时，表头可以无序（效率最高）；当数据无序时，表头必须有序*/
     private static LinkedHashMap<String,String> head;
     /*自定义的Sheet页名称，可以再初始化的时候设置，不设置就是：第几页*/
     private static String sheetNameCustom = null;
     /*自定义的Sheet页名称的页数*/
     private static int sheetPageCustom = 1;

     /*默认格式*/
     static {
         initSheet = new Sheet(1, 0);
         initSheet.setSheetName("第1页");
         /*自适应宽度*/
         initSheet.setAutoWidth(Boolean.TRUE);
     }

     /**
      * @Author:LiuYuKun
      * @Date:2019/12/19 10:04
      * @Description:修改表头时自动换页，当页数据量达到最大时换页（当添加新的表头的时候，可以添加新的Sheet页的名字）
      * @param:sheetNameNew：新的Sheet页的名字，可选，有就使用，没有就延续上一页的名字
      */
     public static void addSheet(String sheetNameNew){
         /*添加新Sheet页的时候将当前页数据的数量清零*/
         currentNum = 0;
         /*获取当前Sheet的页数，在创建新页之前*/
         int sheetNo = initSheet.getSheetNo();
         /*获取当前Sheet的名字，在创建新页之前*/
         String sheetName = initSheet.getSheetName();
         /*创建新的Sheet页并再其上一页页数的基础上加一*/
         initSheet = new Sheet(sheetNo+1, 0);
         /*判断是否有可用的新的名字，有就使用，如果没有就延续上一页的名字*/
         if(sheetNameNew!=null&&!"".equals(sheetNameNew)){
             /*记录并使用新的名字*/
             sheetNameCustom = sheetNameNew;
             initSheet.setSheetName(sheetNameNew);
             /*使用新的名字后，重置当前名字页数*/
             sheetPageCustom = 1;
         }else{
             /*判断之前的名字和默认名字（第几页）是否相同，如果相同就延续页数加一*/
             if(Objects.equals(sheetName,"第"+sheetNo+"页")){
                 initSheet.setSheetName("第"+(sheetNo+1)+"页");
             }else{
                 /*如果不相同，就使用原来的名字，并使其当前名字页数加一*/
                 initSheet.setSheetName(sheetNameCustom+sheetPageCustom);
                 sheetPageCustom++;
             }
         }
         initSheet.setAutoWidth(Boolean.TRUE);
     }
     /**
      * @Author:LiuYuKun
      * @Date:2019/12/19 10:15
      * @Description:设置表头，当数据不同的时候，可自定义设置表头，当更换表头的时候会自动着还行换页
      * @param:head：表头
      * @param:sheetNameNew：新的Sheet页的名字，可选，有就使用，没有就延续上一页的名字
      */
     public void setHead(List<List<String>> head) {
         setHead(head,null);
     }
     public void setHead(List<List<String>> head,String sheetNameNew) {
         if(initSheet != null){
             addSheet(sheetNameNew);
             head0(head);
         }
     }
     public void setHead(String[] head) {
         setHead(head,null);
     }
     public void setHead(String[] head,String sheetNameNew) {
         if(initSheet != null){
             addSheet(sheetNameNew);
             head1(Arrays.asList(head));
         }
     }
     public void setHead(LinkedHashMap<String, String> head) {
         setHead(head,null);
     }
     public void setHead(LinkedHashMap<String, String> head,String sheetNameNew) {
         this.head = head;
         if(initSheet != null){
             addSheet(sheetNameNew);
             head1(head==null ? null:new ArrayList(head.values()));
         }
     }


     /**
      * @Author:LiuYuKun
      * @Date:2019/12/18 10:50
      * @Description:这四种是当传入数据有序时使用，数据data格式为List<LinkedHashMap<String,Object>>、List<List<Object>>时，效率高
      * @param:filePath：下载地址
      * @param:head：表头
      * @param:sheet：设置格式（后两种可设置格式）
      */
     public ExcelUtil(String filePath, List<List<String>> head) {
         open0(filePath,head);
     }
     /*从浏览器获取响应并从浏览器下载excel文件*/
 //    public ExcelUtil(String fileName, List<List<String>> head, HttpServletResponse response){
 //        open2(fileName,head,response);
 //    }
     public ExcelUtil(String filePath, String[] head) {
         open1(filePath,Arrays.asList(head));
     }
     public ExcelUtil(String filePath, List<List<String>> head, Sheet sheet) {
         sheetCustom(sheet);
         open0(filePath,head);
     }
     public ExcelUtil(String filePath, String[] head, Sheet sheet) {
         sheetCustom(sheet);
         open1(filePath,Arrays.asList(head));
     }
     /**
      * @Author:LiuYuKun
      * @Date:2019/12/18 15:15
      * @Description:这两种是当传入数据无序时使用，数据data格式为List<Map<String,Object>>时，效率低
      * @param:filePath：下载地址
      * @param:head：表头
      * @param:sheet：设置格式
      */
     public ExcelUtil(String filePath, LinkedHashMap<String,String> head) {
         this.head = head;
         open1(filePath,head==null ? null:new ArrayList(head.values()));
     }
     public ExcelUtil(String filePath, LinkedHashMap<String,String> head, Sheet sheet) {
         sheetCustom(sheet);
         this.head = head;
         open1(filePath,head==null ? null:new ArrayList(head.values()));
     }


     /**
      * @Author:LiuYuKun
      * @Date:2019/12/18 15:15
      * @Description:自定义Sheet时赋值给公用的initSheet并记录当前的名称
      * @param:sheet：自定义的sheet，模板和格式
      */
     public static void sheetCustom(Sheet sheet){
         if(sheet!=null){
             /*将设置的Sheet模板赋值给公用的initSheet*/
             initSheet = sheet;
             /*将设置的名称赋值给自定义的页名称*/
             sheetNameCustom = sheet.getSheetName();
         }
     }

     /**
      * @Author:LiuYuKun
      * @Date:2019/12/18 15:10
      * @Description:转换并添加表头格式，开启流
      * @param:filePath：下载地址
      * @param:head：表头
      */
     public static void open0(String filePath, List<List<String>> head) {
         head0(head);
         try {
             outputStream = new FileOutputStream(filePath,true);
             writer = EasyExcelFactory.getWriter(outputStream);
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }
     }
     public static void head0(List<List<String>> head){
         if(head!=null&&head.size()!=0){
             initSheet.setHead(head);
         }
     }
     public static void open1(String filePath, List<String> head) {
         head1(head);
         try {
             outputStream = new FileOutputStream(filePath,true);
             writer = EasyExcelFactory.getWriter(outputStream);
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }
     }
     public static void head1(List<String> head){
         if(head!=null&&head.size()!=0){
             /*转换表头格式*/
             List<List<String>> headNew = conversionHead(head);
             initSheet.setHead(headNew);
         }
     }
     /*通过传入表头和流，让其在浏览器页面下载*/
 //    public static void open2(String fileName, List<List<String>> head, HttpServletResponse response){
 //        head0(head);
 //        getOutputStream(fileName, response);
 //        writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX);
 //    }
 //    public static OutputStream getOutputStream(String fileName,HttpServletResponse response){
 //        try {
 //            fileName = URLEncoder.encode(fileName, "UTF-8");
 //            outputStream = response.getOutputStream();
 //            response.reset();
 //
 //            response.setContentType("application/vnd.ms-excel;charset=utf-8");
 //            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
 //            return null;
 //        } catch (IOException e) {
 //            System.out.println(e);
 //        }
 //        return null;
 //    }


     /**
      * @Author:LiuYuKun
      * @Date:2019/12/17 17:20
      * @Description:关闭资源
      */
     public static void close() {
         try {
             if(writer != null){
                 writer.finish();
             }
             if(outputStream != null){
                 outputStream.flush();
                 outputStream.close();
             }
             if(head != null){
                 head.clear();
             }
         } catch (IOException e) {
             System.out.println(e);
         }
     }


     /**
      * @Author:LiuYuKun
      * @Date:2019/12/17 16:04
      * @Description:导出Excel，对数据进行处理，不设置格式
      * @param:filePath：下载地址
      * @param:data：查询出的数据，List格式
      * @param:sheet：Excel页面样式
      */
     public static void excelList(List<List<Object>> data) {
         /*记录当前页的数据，若溢出，则会截取不大于最大条数的数据*/
         List<List<Object>> lastData = data;
         /*记录下一页的数据，若溢出，则会截取溢出的数据*/
         List<List<Object>> nextData = null;
         if(data!=null&&data.size()!=0){
             /*判断当前条数加上传入数据条数是否大于最大条数，如果大于就将数据分割*/
             if(currentNum+data.size()>maxNum){
                 /*记录分割点，也就是没有溢出的数据数量（传入数据量-(当前数据量+传入数据量)-单页最大数据量）*/
                 int num = data.size() - ((currentNum + data.size()) - maxNum);
                 /*记录当前页的数据*/
                 lastData = data.subList(0, num);
                 /*记录下一页的溢出数据*/
                 nextData = data.subList(num, data.size());
             }else{
                 /*如果不大于最大条数，就将数据条数和当前条数相加，相当于下一次的当前条数*/
                 currentNum += data.size();
             }
         }
         /*写入当前页的数据*/
         writer.write1(lastData,initSheet);
         /*判断下一页是否有数据，有就添加下一页并输入数据，没有就关闭资源*/
         judgeNextData(data,nextData);
     }
     /**
      * @Author:LiuYuKun
      * @Date:2019/12/17 16:35
      * @Description:导出Excel，对数据进行处理，不设置格式
      * @param:filePath：下载地址
      * @param:data：查询出的数据，LinkedHashMap格式
      * @param:sheet：Excel页面样式
      */
     public static void excelLinked(List<LinkedHashMap<String,? extends Object>> data) {
         /*保存修改格式后的数据集合*/
         List<List<Object>> dataNew = null;
         /*记录当前页的数据，若溢出，则会截取不大于最大条数的数据*/
         List<List<Object>> lastData = null;
         /*记录下一页的数据，若溢出，则会截取溢出的数据*/
         List<List<Object>> nextData = null;
         if(data!=null&&data.size()!=0){
             /*转换数据格式*/
             dataNew= conversionData(data);
             /*判断当前条数加上传入数据条数是否大于最大条数，如果大于就将数据分割*/
             if(currentNum+dataNew.size()>maxNum){
                 /*记录分割点，也就是没有溢出的数据数量（传入数据量-(当前数据量+传入数据量)-单页最大数据量）*/
                 int num = dataNew.size() - ((currentNum + dataNew.size()) - maxNum);
                 /*记录当前页的数据*/
                 lastData = dataNew.subList(0, num);
                 /*记录下一页的数据*/
                 nextData = dataNew.subList(num, dataNew.size());
             }else{
                 /*如果没有溢出，则当前页数据就是所有数据*/
                 lastData = dataNew;
                 /*如果不大于最大条数，就将数据条数和当前条数相加，相当于下一次的当前条数*/
                 currentNum += lastData.size();
             }
         }
         /*写入当前页的数据*/
         writer.write1(lastData,initSheet);
         /*判断下一页是否有数据，有就添加下一页并输入数据，没有就关闭资源*/
         judgeNextData(data,nextData);
         dataNew.clear();
     }
     /**
      * @Author:LiuYuKun
      * @Date:2019/12/17 16:42
      * @Description:导出Excel，对数据进行处理，不设置格式
      * @param:filePath：下载地址
      * @param:data：查询出的数据，Map格式
      * @param:head：表头数据
      * @param:sheet：Excel页面样式
      */
     public static void excelMap(List<Map<String,? extends Object>> data) {
         /*保存修改格式后的数据集合*/
         List<List<Object>> dataNew = null;
         /*记录当前页的数据，若溢出，则会截取不大于最大条数的数据*/
         List<List<Object>> lastData = null;
         /*记录下一页的数据，若溢出，则会截取溢出的数据*/
         List<List<Object>> nextData = null;
         if(data!=null&&data.size()!=0){
             /*转换数据格式，保存修改格式后的数据集合*/
             dataNew = conversionData(data,head);    // 根据表头拼接数据，让表头和数据对应
             /*判断当前条数加上传入数据条数是否大于最大条数，如果大于就将数据分割*/
             if(currentNum+dataNew.size()>maxNum){
                 /*记录分割点，也就是没有溢出的数据数量（传入数据量-((当前数据量+传入数据量)-单页最大数据量)）*/
                 int num = dataNew.size() - ((currentNum + dataNew.size()) - maxNum);
                 /*获取当前页的数据*/
                 lastData = dataNew.subList(0, num);
                 /*获取下一页溢出的数据*/
                 nextData = dataNew.subList(num, dataNew.size());
             }else{
                 /*如果没有溢出，则当前页数据就是所有数据*/
                 lastData = dataNew;
                 /*如果不大于最大条数，就将数据条数和当前条数相加，相当于下一次的当前条数*/
                 currentNum += lastData.size();
             }
         }
         /*写入当前页的数据*/
         writer.write1(lastData,initSheet);
         /*判断下一页是否有数据，有就添加下一页并输入数据，没有就关闭资源*/
         judgeNextData(data,nextData);
         dataNew.clear();
     }

     /**
      * @Author:LiuYuKun
      * @Date:2019/12/20 12:45
      * @Description:判断是否有下一页数据，如果有就添加下一页并将数据传过去，没有就关闭资源
      * @param:data：数据
      * @param:nextData：下一页数据
      */
     public static void judgeNextData(List data,List<List<Object>> nextData){
         /*如果有溢出数据（下一页有数据）则增加Sheet页，并将溢出数据传入添加数据*/
         if(nextData!=null){
             /*提取当前表头*/
             List<List<String>> head = initSheet.getHead();
             /*添加下一页*/
             addSheet(null);
             /*为新的Sheet设置表头*/
             initSheet.setHead(head);
             /*将下一页的数据传入并写入*/
             excelList(nextData);
         }else{
             /*当没有下一页数据时，关闭当前数据*/
             data.clear();
         }
     }

     /**
      * @Author:LiuYuKun
      * @Date:2019/12/17 16:58
      * @Description:转换数据格式（一），LinkedHashMap类型
      * @param:data：数据
      */
     /*List<?> list 可以赋值任何类型，但是不能添加具体的类型（创建集合的时候可以不设置泛型）
       List<Object> list 只能赋值List<Object>，但是可以添加任何类型
       List<? extends Object> list 与 List<?> list没区别*/
     public static List<List<Object>> conversionData(List<LinkedHashMap<String,? extends Object>> data) {
         List<List<Object>> dataNew = new ArrayList(data.size());
         for(Map<String,? extends Object> map:data){
             List<Object> list = new ArrayList(map.size());
             for(Map.Entry<String,? extends Object> entry:map.entrySet()){
                 list.add(entry.getValue()==null ? "":entry.getValue());
             }
             dataNew.add(list);
         }
         data.clear();
         return dataNew;
     }
     /**
      * @Author:LiuYuKun
      * @Date:2019/12/17 16:07
      * @Description:转换数据格式（二）,HashMap类型，根据表头整理数据顺序
      * @param:data：数据
      * @param:head：表头
      */
     public static List<List<Object>> conversionData(List<Map<String,? extends Object>> data,LinkedHashMap<String,String> head){
         /*将每一行的Map格式转换为List格式，去掉字段名，长度为数据长度，长度和data长度相同*/
         List<List<Object>> dataList = new ArrayList(data.size());
         /*遍历数据*/
         for(Map<String,? extends Object> map : data){
             /*保存每一行的数据（相当于data中的Map），长度和表头长度相同*/
             List<Object> lineList = new ArrayList(head.size());
             /*遍历表头，按表头顺序添加数据，设置遍历标签*/
             label:
             for(Map.Entry<String,String> string : head.entrySet()){
                 for(Map.Entry<String,? extends Object> entry : map.entrySet()){
                     /*当当前表头和当前列中的字段名相同时，将数据存入集合，并跳回标签位置，遍历下一个表头*/
                     if(Objects.equals(string.getKey(),entry.getKey())){
                         lineList.add(entry.getValue()==null ? "":entry.getValue());
                         continue label;
                     }
                 }
             }
             dataList.add(lineList);
         }
         data.clear();
         return dataList;
     }
     /**
      * @Author:LiuYuKun
      * @Date:2019/12/17 16:05
      * @Description:转换表头格式
      * @param:data：数据
      */
     public static List<List<String>> conversionHead(List<String> head) {
         List<List<String>> headNew = new ArrayList(head.size());
         /*将表头List<String>转换为List<List<String>>集合*/
         head.forEach(h -> headNew.add(Collections.singletonList(h)));
         return headNew;
         /*Arrays.asList(strArray)返回值是仍然是一个可变的集合，但是返回值是其内部类，不具有add方法，可以通过set方法进行增加值，默认长度是10
         Collections.singletonList()返回的是不可变的集合，但是这个长度的集合只有1，可以减少内存空间。但是返回的值依然是Collections的内部实现类，同样没有add的方法，调用add，set方法会报错*/
     }






     /**
      * @Author：LiuYuKun
      * @Date：2020/1/9 10:24
      * @Description：模板对象映射导出excel
      * @param data：数据，集合中存储的继承了BaseRowModel的实体类
      */
     public static void writeWithTemplateAndSheet(List<? extends BaseRowModel> data){
         /*判断传入数据是否为空*/
         if(CollectionUtils.isEmpty(data)){
             return;
         }

         initSheet.setClazz(data.get(0).getClass());
         writer.write(data,initSheet);


     }

     /**
      * @Author：LiuYuKun
      * @Date：2020/1/9 11:39
      * @Description：模板对象映射生成多sheet页的excel，将传入数据分sheet页写入excel
      */
     public static void writeWithMultipleSheel(String filePath,List<MultipleSheelPropety> multipleSheelPropetys){
         if(CollectionUtils.isEmpty(multipleSheelPropetys)){
             /*将继承BaseRowModel的实体类的class对象传入，设置sheet页的对象类型*/
             initSheet.setClazz(TestStudent.class);
             return;
         }
         OutputStream outputStream = null;
         ExcelWriter writer = null;
         try {
             outputStream = new FileOutputStream(filePath);
             writer = EasyExcelFactory.getWriter(outputStream);
             for (MultipleSheelPropety multipleSheelPropety : multipleSheelPropetys) {
                 /*当传入的参数中不存在sheet对象时，使用默认的initSheet对象*/
                 Sheet sheet = multipleSheelPropety.getSheet() != null ? multipleSheelPropety.getSheet() : initSheet;
                 /*判断当前实体类中的参数是否为空*/
                 if(!CollectionUtils.isEmpty(multipleSheelPropety.getData())){
                     /*设置参数中实体类的类型*/
                     sheet.setClazz(multipleSheelPropety.getData().get(0).getClass());
                 }
                 writer.write(multipleSheelPropety.getData(), sheet);
             }

         } catch (FileNotFoundException e) {
             System.out.println(e);
         } finally {
             try {
                 if(writer != null){
                     writer.finish();
                 }

                 if(outputStream != null){
                     outputStream.close();
                 }
             } catch (IOException e) {
                 System.out.println(e);
             }
         }

     }





     /**
      * @Author：LiuYuKun
      * @Date：2020/1/9 14:17
      * @Description：匿名内部类，用来包装实体类和Sheet对象，可以提取出去
      */
     @Data
     public static class MultipleSheelPropety{
         private List<? extends BaseRowModel> data;
         private Sheet sheet;
     }

     /**
      * 解析监听器，
      * 每解析一行会回调invoke()方法。
      * 整个excel解析结束会执行doAfterAllAnalysed()方法
      *
      * @author: chenmingjian
      * @date: 19-4-3 14:11
      */
     @Getter
     @Setter
     public static class ExcelListener extends AnalysisEventListener {

         private List<Object> datas = new ArrayList<>();

         /**
          * 逐行解析
          * object : 当前行的数据
          */
         @Override
         public void invoke(Object object, AnalysisContext context) {
             //当前行
             // context.getCurrentRowNum()
             if (object != null) {
                 datas.add(object);
             }
         }

         /**
          * 解析完所有数据后会调用该方法
          */
         @Override
         public void doAfterAllAnalysed(AnalysisContext context) {
             initSheet.setHead(new ArrayList(){{
                 add(Arrays.asList("学生编号"));
                 add(Arrays.asList("学生姓名"));
                 add(Arrays.asList("学生性别"));
                 add(Arrays.asList("学生年龄"));
             }});
             initSheet.setClazz(TestStudent.class);
             initSheet.setSheetName("第一页");
             //解析结束销毁不用的资源
             writer.finish();
             try {
                 outputStream.flush();
                 outputStream.close();
             } catch (IOException e) {
                 System.out.println(e);
             }

         }

     }

     /************************匿名内部类结束，可以提取出去***************************/

 }
