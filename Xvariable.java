import java.util.Scanner;
public class Xvariable
{
  double coefficient;
  double power;
  
  public Xvariable()
  {
    this.coefficient = 0;
    this.power = 0;
  }
  
  
  public Xvariable(double c, double p)
  {
    this.coefficient = c;
    this.power = p;
  }
  
  public Xvariable stringToX(String s)
  {
    //s = s.trim();
    Xvariable result = new Xvariable(1,1);
    int coeffMultiplier = 1;
    
    if(s.toLowerCase().equals("x"))
    {
      result.setPow(1);
      result.setCoeff(1);
      return result;
    }
    
    if(!s.contains("^"))
    {
      
      result.setPow(1);
    }
    else
    {
      //System.out.println(s.contains("^"));
      int j=s.indexOf("^")+1;
      String xPow ="";
      while(j<s.length())
      {
        xPow+=""+s.charAt(j);
        //System.out.println(s.charAt(j));
        j++;
      }
      //System.out.println("a "+xPow);
      //System.out.println(Integer.parseInt(xPow));
      result.setPow((double)Integer.parseInt(xPow));
      //System.out.println("power "+result.power);
      
    }
    //System.out.println("power "+result.power);
    if(!s.toLowerCase().contains("x"))
    {
      //System.out.println("hey "+!s.toLowerCase().contains("x"));
      result.setPow(0);
      //System.out.println("Fuck you");
    }
    String coeffX = "";
    int i=0;
    while(s.charAt(i)!='x')
    {
      coeffX+=""+s.charAt(i);
      i++;
      if(i==s.length())
      {
        break;
      }
    }
    if(!coeffX.equals(""))
    {
      result.setCoeff(Integer.parseInt(coeffX));
    }
    //System.out.println(result.coefficient + " "+result.power);
    return result;
  }
  
  public void setPow(double p)
  {
    this.power = p;
  }
  
  public void setCoeff(double c)
  {
    this.coefficient = c;
  }
  
  public void differentiate()
  {
    if(this.power==0)
    {
      this.setCoeff(0);
      this.setPow(0);
      return;
    }
    this.setCoeff(this.coefficient*this.power);
    this.setPow(this.power-1);
  }
  
  public void integrate()
  {
    this.power+=1;
    this.coefficient=this.coefficient/this.power;
  }
  
  public double evaluate(double s)
  {
    return this.coefficient*(Math.pow(s,this.power));
  }
  
  public Xvariable multiply(Xvariable x)
  {
    return new Xvariable(this.coefficient*x.coefficient, this.power+x.power);
  }
  
  public String toString()
  {
    if(this == null)
    {
      return "";
    }
    if(this.power==0&&this.coefficient!=0)
    {
      return ""+(int)this.coefficient;
    }
    else if(this.coefficient==0)
    {
      return "";
    }
    String result = "x";
    
    if(this.power%1==0 && this.power!=1)
    {
      result+="^"+(int)this.power;
    }
    if(this.coefficient%1==0 && this.coefficient!=1)
    {
      result= (int)this.coefficient+result;
    }
    return result;
  }
  
  public static void main(String[] args)
  {
    Xvariable testing = new Xvariable(3,5);
    //System.out.println(testing);
    Scanner ob = new Scanner(System.in);
    String m = ob.nextLine();
    Xvariable newX = new Xvariable();
    //System.out.println(m.indexOf("^") + " "+ !m.contains("x"));
    newX = newX.stringToX(m);
    System.out.println(newX);
    //newX.differentiate();
    System.out.println(newX);
    System.out.println(newX.evaluate(2));
    System.out.println(newX.power);
    newX.integrate();
    System.out.println(newX);
  }
  
  
}