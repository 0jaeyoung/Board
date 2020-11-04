package board;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

@WebServlet("/Board1")
public class Board1 extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream is = null;
		SqlSessionFactory factory = null;
		SqlSession session = null;
		try {
			String config = "board/mybatis/config.xml";
			is = Resources.getResourceAsStream(config);
			factory = new SqlSessionFactoryBuilder().build(is);
			session = factory.openSession();
			
			List<Board> boardList = session.selectList("BOARD.getBoardList");
			ObjectMapper mapper = new ObjectMapper();
			String resData = "{\"result\":\"true\",\"boardList\":";
			resData+=mapper.writeValueAsString(boardList);
			resData+="}";
			response.setContentType("application/json");
			response.setCharacterEncoding("utf8");
			response.getWriter().print(resData);
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (is != null) try {is.close();} catch(Exception e) {}
			if (session != null) try {session.close();} catch(Exception e) {}
		}
	}
}
