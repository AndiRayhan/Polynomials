import java.util.Scanner;
import java.util.ArrayList;
public class Polynomial {
 ArrayList<Variable> variables = new ArrayList<>();
   
   public Polynomial addition(Polynomial p)
   {
     Polynomial result = new Polynomial();
     for(int i=0;i<this.variables.size();i++)
     {
       Variable newX = this.variables.get(i);
       for(int j=0;j<p.variables.size();j++)
       {
         if(newX.power == p.variables.get(j).power)
         {
           newX.coefficient+=p.variables.get(j).coefficient;
         }
         
       }
       result.variables.add(newX);
     }
     return result;
   }
   
   public void simplify()
   {
     for(int i=0;i<this.variables.size();i++)
     {
       for(int j=i+1;j<this.variables.size();j++)
       {
         if(this.variables.get(i).power == this.variables.get(j).power)
         {
           this.variables.get(i).coefficient+=this.variables.get(j).coefficient;
           this.variables.remove(j);
           j--;
         }
       }
     }
   }
   
   public void sort()
   {
     if(this.variables.size()>1)
     {
       for(int i=1;i<this.variables.size();i++)
       {
         for(int j = i-1;j>=0&&this.variables.get(j).power<this.variables.get(j+1).power;j--)
         {
           Variable temp = this.variables.get(j+1);
           this.variables.set(j+1,this.variables.get(j));
           this.variables.set(j,temp);
         }
       }
       
       
     }
   }
   
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
       Variable myX = new Variable();
       this.variables.add(myX.stringToX(result));
       
       
     }
   }
   public void differentiate()
   {
     for(int i=0;i<this.variables.size();i++)
     {
       this.variables.get(i).differentiate();
     }
     this.sort();
     this.simplify();
   }
   
   public String defIntegrate()
   {
     for(int i=0;i<this.variables.size();i++)
     {
       this.variables.get(i).integrate();
     }
     return this.toString()+" + c";
   }
   
   public double integrate(double lower, double upper)
   {
     Polynomial intPoly = new Polynomial();
     for(int i=0;i<this.variables.size();i++)
     {
       Variable myX = this.variables.get(i);
       myX.integrate();
       intPoly.variables.add(myX);
     }
     double b = 0;
     double a = 0;
     for(int i=0;i<intPoly.variables.size();i++)
     {
       b+=intPoly.variables.get(i).evaluate(upper);
       a+=intPoly.variables.get(i).evaluate(lower);
     }
     return b-a;
   }
   
   
   public String toString()
   {
     String result = "";
     if(this.variables.size()==1 && this.variables.get(0).coefficient==0)
     {
       return "0";
     }
     result+=variables.get(0).toString();
     for(int i=1;i<this.variables.size();i++)
     {
       if(this.variables.get(i).coefficient >0)
       {
         result+=" + ";
       }
       result+=variables.get(i).toString();
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
   
   public Polynomial multiply(Polynomial p)
   {
     Polynomial newPoly = new Polynomial();
     for(int i=0;i<this.variables.size();i++)
     {
       for(int j=0;j<p.variables.size();j++)
       {
         Variable toAdd = this.variables.get(i).multiply(p.variables.get(j));
         newPoly.variables.add(toAdd);
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
       Polynomial pol_one = new Polynomial();
       Scanner hey = new Scanner(System.in);
       System.out.println("Due to Andi's laziness I can only differentiate simple Polynomials.");
       System.out.println("Please enter the function you wish to differentiate "+
                          "in the following format: 3x^2 + 5x - 300");
       String ba = hey.nextLine();
       pol_one.stringToPolynomial(ba);
       System.out.println(pol_one.variables);
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
         Polynomial pol_one = new Polynomial();
         Scanner input = new Scanner(System.in);
         System.out.println("Please enter the Polynomial that you wish to integrate (note that"+
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
         Polynomial myPoly = new Polynomial();
         System.out.println("Please enter the function you wish to integrate.");
         String s = userInput.nextLine();
         myPoly.stringToPolynomial(s);
         System.out.println(myPoly.variables);
         System.out.println("The integrated function is: "+myPoly.defIntegrate());
       }
       
       
     }
   }
 
}

class Variable{
 double coefficient;
   double power;
   
   public Variable()
   {
     this.coefficient = 0;
     this.power = 0;
   }
   
   
   public Variable(double c, double p)
   {
     this.coefficient = c;
     this.power = p;
   }
   
   public Variable stringToX(String s)
   {
     //s = s.trim();
     Variable result = new Variable(1,1);
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
       int j=s.indexOf("^")+1;
       String xPow ="";
       while(j<s.length())
       {
         xPow+=""+s.charAt(j);
         j++;
       }
       result.setPow((double)Integer.parseInt(xPow));
       
     }
     if(!s.toLowerCase().contains("x"))
     {
       result.setPow(0);
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
   
   public Variable multiply(Variable x)
   {
     return new Variable(this.coefficient*x.coefficient, this.power+x.power);
   }
   
   public String toString()
   {
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
     else{
      result = this.coefficient + result;
     }
     return result;
   }
   
}
