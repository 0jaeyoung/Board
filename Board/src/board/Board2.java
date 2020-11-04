package board;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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

@WebServlet("/Board2")
public class Board2 extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream is = null;
		SqlSessionFactory factory = null;
		SqlSession session = null;
		try {
			String config = "board/mybatis/config.xml";
			is = Resources.getResourceAsStream(config);
			factory = new SqlSessionFactoryBuilder().build(is);
			session = factory.openSession();
			
			InputStream inputStream = request.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			Board reqData = mapper.readValue(inputStream, Board.class);
			String boardId = reqData.getBoardId();
			
			Board boardDetail = session.selectOne("BOARD.getBoardDetail", boardId);
			String resData = mapper.writeValueAsString(boardDetail);
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
