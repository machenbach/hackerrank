import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.security.Permission;

import javax.annotation.Generated;


public class Solution {

	public static void main(String[] args) throws Exception {
		Do_Not_Terminate.forbidExit();	

		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			int num=Integer.parseInt(br.readLine().trim());
			Object o;// Must be used to hold the reference of the instance of the class Solution.Inner.Private

// my code here
			Inner inInst = new Inner();
			Class<Inner> c = Inner.class;
			Class<?> [] l = c.getDeclaredClasses();
			Class<?> ip = l[0];
			Constructor<?>[] ipcs = ip.getDeclaredConstructors();
			Constructor<?> con = ipcs[0];
			con.setAccessible(true);
			o = ipcs[0].newInstance(inInst);
			System.out.println(((Solution.Inner.Private)o).powerof2(num));
			
			System.out.println("An instance of class: "+o.getClass().getCanonicalName()+" has been created");
			
			}//end of try
			
			catch (Do_Not_Terminate.ExitTrappedException e) {
				System.out.println("Unsuccessful Termination!!");
			}
		}//end of main
		static class Inner
		{
			private class Private{
				private String powerof2(int num)
				{
					return ((num&num-1)==0)?"power of 2":"not a power of 2";
				}
			}
		}//end of Inner
		
	}//end of Solution

	class Do_Not_Terminate {
		 
	    public static class ExitTrappedException extends SecurityException {

			private static final long serialVersionUID = 1L;
	    }
	 
	    public static void forbidExit() {
	        final SecurityManager securityManager = new SecurityManager() {
	            @Override
	            public void checkPermission(Permission permission) {
	                if (permission.getName().contains("exitVM")) {
	                    throw new ExitTrappedException();
	                }
	            }
	        };
	        System.setSecurityManager(securityManager);
	    }
	}	
		


