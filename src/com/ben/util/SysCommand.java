package com.ben.util;

import java.io.IOException;

public class SysCommand {
	public static int pingHost(String str) {
		
//		  -d   ʹ��Socket��SO_DEBUG���ܡ�
//		  -c<��ɴ���>   �������Ҫ���Ӧ�Ĵ�����
//		  -f   ���޼�⡣
//		  -i<�������>   ָ���շ���Ϣ�ļ��ʱ�䡣
//		  -I<�������>   ʹ��ָ������������ͳ����ݰ���
//		  -l<ǰ������>   �������ͳ�Ҫ����Ϣ֮ǰ�����з��������ݰ���
//		  -n   ֻ�����ֵ��
//		  -p<������ʽ>   �����������ݰ��ķ�����ʽ��
//		  -q   ����ʾָ��ִ�й��̣���ͷ�ͽ�β�������Ϣ���⡣
//		  -r   ������ͨ��Routing Table��ֱ�ӽ����ݰ��͵�Զ�������ϡ�
//		  -R   ��¼·�ɹ��̡�
//		  -s<���ݰ���С>   �������ݰ��Ĵ�С��
//		  -t<�����ֵ>   ���ô����ֵTTL�Ĵ�С��
//		  -v   ��ϸ��ʾָ���ִ�й��̡�
		
		
		int result = 200;
		try {
			System.out.println("��ʼ���ԣ�");

			Process p = Runtime.getRuntime().exec("ping -c 1 -i 0.2 -w 5 " + str);
			System.out.println("���Եȴ��У�");

			int status = p.waitFor();
			if (status == 0) {
				System.out.println("���ӳɹ���");
			} else {
				result = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}

}
