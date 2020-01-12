import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.*;

class Unpacker
{
	public static void main(String arg[]) throws Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter file name");
		Unpacker obj=new Unpacker(br.readLine());
	}
	public Unpacker(String src) throws Exception
	{
			String ext=src.substring(src.lastIndexOf(".")+1);
			if(!ext.toUpperCase().equals("TXT"))
			{
				System.out.println(" Wrong file type");
				return;
			}
			
			
			FileInputStream ufile=new FileInputStream(src);
			byte[] header=new byte[100];
			
			byte[] check=new byte[9];
			ufile.read(check);
			
			for(int i=0;i<check.length;i++)
			{
				check[i]=(byte)(check[i]^5);
			}
			String checkstring=new String(check);
			if(!checkstring.equals("Nikhil004"))								// checking for valid file
			{
				System.out.println("This is not valid file");
				return;
			}
			
			while(ufile.read(header)>=0)
			{
				for(int index=0;index<header.length;index++)
				{
					header[index]=(byte)(header[index]^5);						// Decrypting header
				}
				
				String sheader=new String(header);
				String[] tokens=sheader.split(" ");
				//System.out.println(tokens[0]);
				//System.out.println(tokens[1]);
				byte[] data=new byte[Integer.parseInt(tokens[1])];
				ufile.read(data);
				
				for(int index=0;index<data.length;index++)
				{
					data[index]=(byte)(data[index]^5);							// Decrypting data
				}
				
				//System.out.println(new String(data));
				FileOutputStream fout=new FileOutputStream(tokens[0]);
				fout.write(data);
			}
			
	}
}