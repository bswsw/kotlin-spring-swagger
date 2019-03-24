package com.baegoony.springswaggerdemo.domain.team

import com.baegoony.springswaggerdemo.base.BaseEntity
import io.swagger.annotations.ApiModelProperty
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Team(
    @ApiModelProperty("팀명", required = true, example = "A팀")
    @Column(name = "team_name")
    var name: String,
    @ApiModelProperty("팀 브랜드", required = true, example = "나이키")
    var maker: String,
    @ApiModelProperty("팀 생성 연도", required = true, example = "2019")
    var year: Int
) : BaseEntity()
