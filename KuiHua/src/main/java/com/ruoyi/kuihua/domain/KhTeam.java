package com.ruoyi.kuihua.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import com.ruoyi.common.core.domain.BaseEntity;
import java.io.Serializable;
/**
 * 团队管理对象 kh_team
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@TableName(value ="kh_team")
@Data
public class KhTeam implements Serializable
{
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /** 团队ID */
    @Excel(name = "团队ID")
    @TableId(type = IdType.AUTO)
    private Long teamId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 团队名称 */
    @Excel(name = "团队名称")
    private String teamName;

    /** 团队邀请码 */
    @Excel(name = "团队邀请码")
    private String teamCode;

    /** 团队指标 */
    @Excel(name = "团队指标")
    private Long teamKpi;

    /** 团队总得分 */
    @Excel(name = "团队总得分")
    private Long teamAllScore;

    public void addScore(Long score){
        this.teamAllScore += score;
    }

    public KhLeaderBoardVo toLeaderBoardVo(Long index){
        KhLeaderBoardVo leaderBoardVo = new KhLeaderBoardVo();
        leaderBoardVo.setTeamId(this.teamId);
        leaderBoardVo.setTeamName(this.teamName);
        leaderBoardVo.setScore(this.teamAllScore);
        leaderBoardVo.setSortIndex(index);
        return leaderBoardVo;
    }

}
