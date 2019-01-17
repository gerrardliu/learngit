package tk.mybatis.web.mapper;

import org.apache.ibatis.session.RowBounds;
import tk.mybatis.web.model.SysDict;

import java.util.List;

public interface DictMapper {

    SysDict selectByPrimaryKey(Long id);

    List<SysDict> selectBySysDict(SysDict sysDict, RowBounds rowBounds);

    int insert(SysDict sysDict);

    int updateById(SysDict sysDict);

    int deleteById(Long id);
}
