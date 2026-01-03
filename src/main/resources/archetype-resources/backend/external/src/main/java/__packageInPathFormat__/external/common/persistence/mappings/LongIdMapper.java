package ${package}.external.common.persistence.mappings;

import org.springframework.stereotype.Component;

@Component
public class LongIdMapper implements com.mycompany.external.common.persistence.mapping.IdMapper<Long, Long> {
    @Override
    public Long toDbId(Long id) {
        return id;
    }
}
