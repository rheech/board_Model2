package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import article.model.ArticleContent;
import jdbc.JdbcUtil;
/*
 * article_content table insert 클래스
 * 
 * insert 성공 시 article_content 객체 리턴
 * 
 *나중에 validation check 할 때 쓸 것.
 * */

public class ArticleContentDao {
	public ArticleContent insert(Connection conn,ArticleContent content) throws SQLException{
		PreparedStatement pstmt = null;
		
		try{
			pstmt = conn.prepareStatement("insert into article_content (article_no,content) values(?,?)");
			pstmt.setLong(1, content.getNumber());
			pstmt.setString(2, content.getContent());
			
			int insertedCount = pstmt.executeUpdate();
			
			if(insertedCount>0){
				return content;
			}else{
				return null;
			}
			
		}finally{
			JdbcUtil.close(pstmt);
		}
		
	}
	
	public ArticleContent selectById(Connection conn,int no) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			pstmt = conn.prepareStatement("select * from article_content where article_no = ?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			ArticleContent content = null;
			if(rs.next()){
				content = new ArticleContent(rs.getInt("article_no"),rs.getString("content"));
			}
			return content;
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
}