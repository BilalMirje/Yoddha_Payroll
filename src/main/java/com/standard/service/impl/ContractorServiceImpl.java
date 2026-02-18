package com.standard.service.impl;

import com.standard.entity.Contractor;
import com.standard.entity.ContractorEvent;
import com.standard.entity.dtos.contractor.ContractorRequest;
import com.standard.entity.dtos.eventWorkItem.ContractorPaymentRequest;
import com.standard.entity.dtos.pagination.PageRequest;
import com.standard.exceptions.handlers.DuplicateRecordException;
import com.standard.exceptions.handlers.ResourceNotFoundException;
import com.standard.mappers.ContractorMapper;
import com.standard.payload.ApiResponse;
import com.standard.payload.ApiResponseUtil;
import com.standard.queryHelper.QueryResultHandler;
import com.standard.repository.ContractorEventRepository;
import com.standard.repository.ContractorRepository;
import com.standard.service.ContractorService;
import com.standard.uploads.S3ImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;
    private final ContractorMapper mapper;
    private final S3ImageUpload s3ImageUpload;
    private final QueryResultHandler queryResultHandler;

    @Override
    public ApiResponse<?> create(ContractorRequest request, MultipartFile profilePhoto) {

        if (contractorRepository.existsByAadharNumber(request.getAadharNumber())) {
            throw new DuplicateRecordException("Aadhar already registered");
        }

        Contractor contractor = mapper.toEntity(request);

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String url = s3ImageUpload.uploadImageToS3(profilePhoto);
            contractor.setProfilePhoto(url);
        }

        contractorRepository.save(contractor);

        return ApiResponseUtil.created(mapper.toResponse(contractor));
    }

    @Override
    public ApiResponse<?> update(Long id, ContractorRequest request, MultipartFile profilePhoto) {

        Contractor contractor = contractorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contractor not found"));

        if (!contractor.getAadharNumber().equals(request.getAadharNumber())
                && contractorRepository.existsByAadharNumber(request.getAadharNumber())) {

            return ApiResponseUtil.duplicateRecord(
                    "duplicate",
                    "Aadhar already registered with another contractor",
                    null
            );
        }

        mapper.updateEntity(contractor, request);

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String url = s3ImageUpload.uploadImageToS3(profilePhoto);
            contractor.setProfilePhoto(url);
        }

        contractorRepository.save(contractor);

        return ApiResponseUtil.updated(mapper.toResponse(contractor));
    }

    @Override
    public ApiResponse<?> getById(Long id) {
        return contractorRepository.findById(id)
                .map(c -> ApiResponseUtil.fetched(mapper.toResponse(c)))
                .orElse(ApiResponseUtil.notFound("not_found","Contractor not found",null));
    }

    @Override
    public ApiResponse<?> delete(Long id) {
        if (!contractorRepository.existsById(id))
            throw new ResourceNotFoundException("Contractor not found");

        contractorRepository.deleteById(id);
        return ApiResponseUtil.deleted();
    }

    @Override
    public ApiResponse<?> getAll(PageRequest pageRequest) {

        return queryResultHandler.fetchAndRespond(
                pageRequest,
                pageable -> contractorRepository.findAll(pageable),
                sort -> contractorRepository.findAll(sort),
                () -> contractorRepository.findAll(),
                mapper::toResponse,
                "no record found"
        );
    }


}