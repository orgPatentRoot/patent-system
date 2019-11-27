package com.suixingpay.patent.mapper;

import com.suixingpay.patent.pojo.Index;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IndexMapper {
    /*
     * 查询此用户所有的专利的指标
     * */
    //select * from index i,patent p where i.index_patent_id = p.index_patent_id
    //@Select("SELECT index_id,index_patent_id,index_content,index_create_time from `index` i, patent p WHERE i.index_patent_id = p.patent_id")
    //@Select("SELECT index_id,index_patent_id,index_content,index_create_time from `index`")
//    @Results(id="resultMap", value={
//            @Result(column="index_id", property="indexId", id=true),
//            @Result(column="index_patent_id", property="indexPatentId"),
//            @Result(column="index_content", property="indexContent"),
//            @Result(column="index_create_time", property="indexCreateTime")})
    List<Index> queryAllIndex();

    /*
     * 根据专利id，查询此专利的指标
     * */
 /*   @Select("select index_id,index_patent_id,index_content,index_create_time from `index` Where index_patent_Id = #{patentId}")
    @ResultMap(value={"resultMap"})*/
    List<Index> selectIndexByPatentId(Integer patentId);

   // List<Index> userFindPatentIndex();


    /*
     * 插入一条指标
     * */
//    @Insert("insert into `index`(index_patent_id,index_content,index_create_time) values(#{index.indexPatentId}, #{index.indexContent}, now())")
//    @Options(useGeneratedKeys=true,keyColumn="")
//    @ResultMap(value={"resultMap"})
    int insertIndexContent(Index index);

    /*
     * 根据专利id，修改指标
     * */
//    @Update("update `index` set index_content = #{index.indexContent}, index_create_time = #{index.indexCreateTime} where index_patent_id = #{patentId}")
//    @ResultMap(value={"resultMap"})
    int updateIndexContent(Index inedx);

    /*
     * 根据专利id，删除指标
     * */
//    @Delete("delete from `index` where index_id = #{patentId}")
//    @ResultMap(value={"resultMap"})
    int deleteIndex(Integer patentId);
}