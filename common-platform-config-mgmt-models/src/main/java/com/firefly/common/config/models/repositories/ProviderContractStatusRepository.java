package com.firefly.common.config.models.repositories;

import com.firefly.common.config.models.entities.ProviderContractStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderContractStatusRepository extends BaseRepository<ProviderContractStatus, Long> {

    Flux<ProviderContractStatus> findByActiveTrue();

    Mono<ProviderContractStatus> findByCode(String code);
}