package com.baegoony.springswaggerdemo.domain.member

import com.baegoony.springswaggerdemo.base.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Member(
    @Column(name = "member_name")
    var name: String
) : BaseEntity()
