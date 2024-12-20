package com.challenge.votation.infra.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig( unmappedTargetPolicy = ReportingPolicy.IGNORE )
public class BaseMapperConfig
{
}
