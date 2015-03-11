import java.util.ArrayList;
import java.util.Scanner;
public class polynomial
{
  //double polyCoefficient;
  //double polyPower;
  //ArrayList<polynomial> polys;
  ArrayList<Xvariable> xVars = new ArrayList<Xvariable>();//what is this what is this
  
  //adding two polynomials
  public polynomial addition(polynomial p)
  {
    polynomial result = new polynomial();
    for(int i=0;i<this.xVars.size();i++)
    {
      Xvariable newX = this.xVars.get(i);
      for(int j=0;j<p.xVars.size();j++)
      {
        if(newX.power == p.xVars.get(j).power)
        {
          newX.coefficient+=p.xVars.get(j).coefficient;
        }
        
      }
      result.xVars.add(newX);
    }
    return result;
  }
  
	//simplifying polynomials -- variables of the same degree will have their coefficients added
  public void simplify()
  {
    for(int i=0;i<this.xVars.size();i++)
    {
      for(int j=i+1;j<this.xVars.size();j++)
      {
        if(this.xVars.get(i).power == this.xVars.get(j).power)
        {
          this.xVars.get(i).coefficient+=this.xVars.get(j).coefficient;
          this.xVars.remove(j);
          j--;
        }
      }
    }
  }
  
  //sort the xVariable objects in the arraylist of xVariable objects in the polynomial
  //mostly for appearance purposes only
  public void sort()
  {
    if(this.xVars.size()>1)
    {
      for(int i=1;i<this.xVars.size();i++)
      {
        for(int j = i-1;j>=0&&this.xVars.get(j).power<this.xVars.get(j+1).power;j--)
        {
          Xvariable temp = this.xVars.get(j+1);
          this.xVars.set(j+1,this.xVars.get(j));
          this.xVars.set(j,temp);
        }
      }
      
      
    }
  }
  
  
  //Parse user input, create new xVariable objects and export them into an already-existing polynomial object
  public void stringToPolynomial(String s)
  {
    s = s.replace(" ","");
    
    for(int i=0;i<s.length();i++)
    {
      if(s.charAt(i)=='+')
      {
        continue;
      }
      String result = "";
      if(s.charAt(i)=='-' && i <=s.length()-1)
      {
        result+="-";
        i++;
      }
      while((s.charAt(i)>='0' && s.charAt(i)<='9')||s.charAt(i)=='x'||s.charAt(i)=='^')
      {
        result +=""+s.charAt(i);
        i++;
        if(i==s.length())
        {
          break;
        }
        
      }
      i--;
      Xvariable myX = new Xvariable();
      this.xVars.add(myX.stringToX(result));
      
      
    }
  }
  
  
  //differentiate a polynomial by executing the differentiate() method on each of the xVariable objects
  public void differentiate()
  {
    for(int i=0;i<this.xVars.size();i++)
    {
      this.xVars.get(i).differentiate();
      //mypoli.xVars.add(this.xVars.get(i));
    }
    this.sort();
    this.simplify();
  }
  
  
  //Indefinite integral - will change method name
  public String defIntegrate()
  {
    for(int i=0;i<this.xVars.size();i++)
    {
      this.xVars.get(i).integrate();
    }
    return this.toString()+" + c";
  }
  
  
  //definite integral
  public double integrate(double lower, double upper)
  {
    polynomial intPoly = new polynomial();
    for(int i=0;i<this.xVars.size();i++)
    {
      Xvariable myX = this.xVars.get(i);
      myX.integrate();
      intPoly.xVars.add(myX);
    }
    double b = 0;
    double a = 0;
    for(int i=0;i<intPoly.xVars.size();i++)
    {
      b+=intPoly.xVars.get(i).evaluate(upper);
      a+=intPoly.xVars.get(i).evaluate(lower);
    }
    return b-a;
  }
  
  //toString method of polynomial
  public String toString()
  {
    String result = "";
    if(this.xVars.size()==1 && this.xVars.get(0).coefficient==0)
    {
      return "0";
    }
    result+=xVars.get(0).toString();
    for(int i=1;i<this.xVars.size();i++)
    {
      if(this.xVars.get(i).coefficient >0)
      {
        result+=" + ";
      }
      result+=xVars.get(i).toString();
    }
    return result;
  }
  
  public static int noOccurrences(String s, String sub)
  {
    int b = s.indexOf(sub);
    int k = 0;
    while(b>=0)
    {
      s=s.replaceFirst(sub,"");
      b = s.indexOf(sub);
      k++;
    }
    return k;
  }
  
  public polynomial multiply(polynomial p)
  {
    polynomial newPoly = new polynomial();
    for(int i=0;i<this.xVars.size();i++)
    {
      for(int j=0;j<p.xVars.size();j++)
      {
        Xvariable toAdd = this.xVars.get(i).multiply(p.xVars.get(j));
        newPoly.xVars.add(toAdd);
      }
    }
    newPoly.simplify();
    newPoly.sort();
    return newPoly;
  }
  
  public static void main(String[] args)
  {
    System.out.println("Welcome, user. What would you like to do today? (I can differentiate and integrate!)");
    Scanner userInput = new Scanner(System.in);
    String b = userInput.nextLine();
    while(!(b.toLowerCase().contains("differentiate")^b.toLowerCase().contains("integrate")))
    {
      if(b.toLowerCase().contains("differentiate")&&b.toLowerCase().contains("integrate"))
      {
        System.out.println("Why would you want to do both? Pick one!");
      }
      else
      {
        System.out.println("I'm sorry but I don't understand the command. Please try again.");
      }
      b = userInput.nextLine();
    }
    
    if(b.toLowerCase().contains("differentiate") && !b.toLowerCase().contains("integrate"))
    {
      polynomial pol_one = new polynomial();
      Scanner hey = new Scanner(System.in);
      System.out.println("Due to Andi's laziness I can only differentiate simple polynomials.");
      System.out.println("Please enter the function you wish to differentiate "+
                         "in the following format: 3x^2 + 5x - 300");
      String ba = hey.nextLine();
      pol_one.stringToPolynomial(ba);
      pol_one.differentiate();
      System.out.println("Here is your differentiated function. "+
                         "I hope you don't do anything illgeal with it. "
                           +"But why do I care - I'm not even human."+" : "+pol_one);
    }
    else
    {
      System.out.println("Would you like to do indefinite or definite integral?");
      Scanner yo = new Scanner(System.in);
      String bo = yo.nextLine();
      bo = bo.toLowerCase();
      while(!bo.contains("definite"))
      {
        System.out.println("That was an invalid entry. Please try again.");
        bo = yo.nextLine();
      }
      while(bo.contains("indefinite")&& noOccurrences(bo,"definite")>1 && noOccurrences(bo,"indefinite")<noOccurrences(bo,"definite"))
      {
        System.out.println("Can't do both! Pick one.");
        bo = yo.nextLine();
      }
      
      if(!bo.contains("indefinite"))
      {
        polynomial pol_one = new polynomial();
        Scanner input = new Scanner(System.in);
        //Scanner bounds = new Scanner(System.in);
        System.out.println("Please enter the polynomial that you wish to integrate (note that"+
                           " this is a definite integral - you will need to define bounds)");
        String uInput = input.nextLine();
        pol_one.stringToPolynomial(uInput);
        System.out.println("Please input the lower bound of integration");
        int lower = input.nextInt();
        System.out.println("Now the upper bound");
        int upper = input.nextInt();
        System.out.println("Result = "+pol_one.integrate(lower,upper));
      }
      else
      {
        polynomial myPoly = new polynomial();
        System.out.println("Please enter the function you wish to integrate.");
        String s = userInput.nextLine();
        myPoly.stringToPolynomial(s);
        System.out.println("The integrated function is: "+myPoly.defIntegrate());
      }
      
      /*polynomial polyone = new polynomial();
      polyone.stringToPolynomial("3x^2 + 1");
      polynomial polytwo = new polynomial();
      polytwo.stringToPolynomial("3x^2 -1");
      polynomial polythree = polyone.multiply(polytwo);
      System.out.println(polythree);*/
      
      
    }
  }
    
    
  }
  


