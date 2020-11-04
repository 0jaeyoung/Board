package board;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/Login")
public class Login extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream is = null;
		SqlSessionFactory factory = null;
		SqlSession session = null;
		try {
			// DB ����
			String config = "board/mybatis/config.xml";
			is = Resources.getResourceAsStream(config);
			factory = new SqlSessionFactoryBuilder().build(is);
			session = factory.openSession();
			
			// request �Ķ���� �� �ޱ�
			InputStream inputStream = request.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			HashMap reqData = mapper.readValue(inputStream, HashMap.class);
			String uID = (String) reqData.get("uID");
			String uPW = (String) reqData.get("uPW");
			HashMap<String, String> getUser = session.selectOne("BOARD.getUserById", uID);
			
			// logicó��
			HashMap<String, String> resUser = new HashMap<>();
			if( getUser!=null ) { // ID��ġ
				if( uPW.equals(getUser.get("PASSWD")) ) { // PW��ġ
					resUser.put("result", "true");
					resUser.put("userName", getUser.get("USER_NAME"));
					resUser.put("userType", getUser.get("USER_TYPE"));
				} else { // PW����ġ
					resUser.put("result", "false");
				}
			} else { // ID����ġ
				resUser.put("result", "false");
			}
			
			// response ��ü ����
			String resData = mapper.writeValueAsString(resUser);
			response.setContentType("application/json");
			response.setCharacterEncoding("utf8");
			response.getWriter().print(resData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) try {is.close();} catch(Exception e) {}
			if (session != null) try {session.close();} catch(Exception e) {}
		}
	}
}
