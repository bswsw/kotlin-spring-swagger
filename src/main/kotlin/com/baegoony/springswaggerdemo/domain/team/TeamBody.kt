package com.baegoony.springswaggerdemo.domain.team

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Min

data class TeamBody(
    @ApiModelProperty("팀명", required = true, example = "A팀")
    val name: String,
    @ApiModelProperty("팀 브랜드", required = true, example = "나이키")
    val maker: String,
    @ApiModelProperty("팀 생성 연도", required = true, example = "2019")
    @field:Min(1900)
    val year: Int
)
