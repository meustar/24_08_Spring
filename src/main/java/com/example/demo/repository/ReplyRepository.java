package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.vo.Reply;

@Mapper
public interface ReplyRepository {

	@Select("""
			SELECT R.*, M.nickname AS extra__writer
			FROM reply AS R
			INNER JOIN `member` AS M
			ON R.memberId = M.id
			WHERE relTypeCode = #{relTypeCode}
			AND relId = #{relId}
			ORDER BY R.id ASC;
			""")
	public List<Reply> getForPrintReplies(String relTypeCode, int id);

	@Insert("""
			INSERT INTO reply
			SET regDate = NOW(),
			updateDate = NOW(),
			memberId = #{memberId},
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			`body` = #{body}
			""")
	public void writeReply(int loginedMemberId, String body, String relTypeCode, int relId);

	@Select("SELECT LAST_INSERT_ID();")
	public int getLastInsertId();
	

}
