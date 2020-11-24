package board;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import board.model.Board;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/Board3")
public class Board3 extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream is = null;
		SqlSessionFactory factory = null;
		SqlSession session = null;
		try{
			String config = "board/mybatis/config.xml";
			is = Resources.getResourceAsStream(config);
			factory = new SqlSessionFactoryBuilder().build(is);
			session = factory.openSession();
			
			InputStream inputStream = request.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			Board reqData = mapper.readValue(inputStream, Board.class);
			session.insert("BOARD.registerBoard", reqData);
			session.commit();
			response.getWriter().print("success");
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (is != null) try {is.close();} catch(Exception e) {}
			if (session != null) try {session.close();} catch(Exception e) {}
		}
	}
}
