package com.ruoyi.kuihua.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class KhLeaderBoardVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long teamId;
    private String teamName;
    private Long score;
    private Long sortIndex;
}
