package co.uk.sapient.sapienthometask.service.base;

import co.uk.sapient.sapienthometask.dto.base.BaseDTO;
import co.uk.sapient.sapienthometask.entity.base.BaseEntity;

import java.util.List;

public interface BaseService<E extends BaseEntity, D extends BaseDTO> {

    List<D> getAll();

    E saveOrUpdate(D entity) throws Exception;
}