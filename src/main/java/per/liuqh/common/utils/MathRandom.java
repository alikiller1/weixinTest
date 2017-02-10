package per.liuqh.common.utils;

/**
 * JAVA 返回随机数，并根据概率、比率
 * @author zhanglei
 *
 */
public class MathRandom
{
 /**
     * 0出现的概率为%50
     */
 public static double rate0 = 0.50;
 /**
     * 1出现的概率为%20
     */
 public static double rate1 = 0.20;
 /**
     * 2出现的概率为%15
     */
 public static double rate2 = 0.15;
 /**
     * 3出现的概率为%10
     */
 public static double rate3 = 0.10;
 /**
     * 4出现的概率为%4
     */
 public static double rate4 = 0.04;
 /**
     * 5出现的概率为%1
     */
 public static double rate5 = 0.01;

 /**
  * Math.random()产生一个double型的随机数，判断一下
  * 例如0出现的概率为%50，则介于0到0.50中间的返回0
     * @return int
     *
     */
 private int PercentageRandom()
 {
  double randomNumber;
  randomNumber = Math.random();
  if (randomNumber >= 0 && randomNumber <= rate0)
  {
   return 0;
  }
  else if (randomNumber >= rate0  && randomNumber <= rate0 + rate1)
  {
   return 1;
  }
  else if (randomNumber >= rate0 + rate1
    && randomNumber <= rate0 + rate1 + rate2)
  {
   return 2;
  }
  else if (randomNumber >= rate0 + rate1 + rate2
    && randomNumber <= rate0 + rate1 + rate2 + rate3)
  {
   return 3;
  }
  else if (randomNumber >= rate0 + rate1 + rate2 + rate3
    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4)
  {
   return 4;
  }
  else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4
    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4
      + rate5)
  {
   return 5;
  }
  return -1;
 }

 /**
  * 测试主程序
     * @param agrs
     */
 public static void main(String[] agrs)
 {
  int count=0;
  int i = 0;
  MathRandom a = new MathRandom();
  for (i = 0; i <= 100; i++)//打印100个测试概率的准确性
  {
   int c=a.PercentageRandom();
   if(c==3){
	   count++;
   }
  }
 System.out.println(count);
 }
}


