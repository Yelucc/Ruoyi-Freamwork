package com.ruoyi.kuihua.service.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.kuihua.domain.KhLeaderBoardVo;
import org.springframework.stereotype.Service;
import com.ruoyi.kuihua.mapper.KhTeamMapper;
import com.ruoyi.kuihua.domain.KhTeam;
import com.ruoyi.kuihua.service.KhTeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 团队管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@Service
public class KhTeamServiceImpl extends ServiceImpl<KhTeamMapper, KhTeam>
        implements KhTeamService
{
    /**
     * 获取团队排行榜
     *
     * @return 排行榜
     */
    @Override
    public List<KhLeaderBoardVo> getLeaderBoardVoList() {
        List<KhTeam> khTeams = list(Wrappers.lambdaQuery(KhTeam.class).orderByDesc(KhTeam::getTeamAllScore));
        AtomicLong index = new AtomicLong(1);
        return khTeams.stream().map(item -> item.toLeaderBoardVo(index.getAndIncrement())).collect(Collectors.toList());
    }

}
