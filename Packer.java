import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.*;

class Packer
{
	String[] extention={"c","cpp","java","txt"};
	
	public static void main(String arg[]) throws Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter directory and new file name");
		Packer obj=new Packer(br.readLine(),br.readLine());
	}
	
	public Packer(String src,String dest) throws Exception
	{
		File[] filelist=allfilelist(src);
		if(filelist==null)
		{
			System.out.println("Wrong Directory");
			return;
		}
		String ext=dest.substring(dest.lastIndexOf(".")+1);
		if(!ext.toUpperCase().equals("TXT"))
		{
			System.out.println(" Wrong destination file");
			return;
		}
	
		FileOutputStream fos = new FileOutputStream(dest); 
		String fn;
		byte[] buffer;
		
		
		List<String> extlist=Arrays.asList(extention);
		
		String checkstring="Nikhil004";
		byte[] check=checkstring.getBytes();
		for(int i=0;i<check.length;i++)
		{
			check[i]=(byte)(check[i]^5);
		}
		fos.write(check);													//writing check at the begining of the file to identify our file
		
		for(File i:filelist)
		{
			fn=i.getName();
			if(fn.lastIndexOf(".") != -1 && fn.lastIndexOf(".") != 0)
			{
				
				if(extlist.contains((fn.substring(fn.lastIndexOf(".")+1)).toLowerCase()))
				{
					
					String header	=fn+" "+i.length();
					for(int cnt=header.length();cnt<100;cnt++)
					{
						header=header+" ";
					}
					//System.out.println(header.length());
					try
					{    
							FileInputStream fin=new FileInputStream(i.getPath());    
							
							buffer=new byte[(int)i.length()];
							fin.read(buffer);
							byte[] bheader=new byte[100];
							bheader=header.getBytes();
							for(int index=0;index<bheader.length;index++)
							{
								bheader[index]=(byte)(bheader[index]^5);						// Encrypting header
							}
							for(int index=0;index<buffer.length;index++)
							{
								buffer[index]=(byte)(buffer[index]^5);							// Encrypting data
							}
							
							fos.write(bheader);
						
							fos.write(buffer);
							fin.close();    
					}
					catch(Exception e)
					{
						System.out.println(e);
					}    			
				}
				
			}
			
		}
	}
	private File[] allfilelist(String dirname)
	{
		File dir=new File(dirname);
		if(dir==null)
		{
			return null;
		}
		File[] list=dir.listFiles();
		return list;
	}
}