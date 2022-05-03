package com.override.service;

import com.override.models.Document;
import com.override.models.PlatformUser;
import com.override.repositories.DocumentRepository;
import org.apache.commons.fileupload.FileUploadException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.override.utils.TestFieldsUtil.generateTestDocument;
import static com.override.utils.TestFieldsUtil.generateTestUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private PlatformUserService platformUserService;

    @Test
    public void uploadTest() throws FileUploadException {
        PlatformUser platformUser = generateTestUser();
        MockMultipartFile file = new MockMultipartFile("data",
                "filename.txt",
                "text/plain",
                "some content".getBytes());

        when(platformUserService.findPlatformUserByLogin("Andrey")).thenReturn(platformUser);
        ReflectionTestUtils.setField(documentService, "maxFileSize", 5242880L);

        documentService.uploadFile(file, "Andrey");
        verify(documentRepository, times(1)).save(any());
    }

    @Test
    public void uploadExceptionTest() {
        MockMultipartFile file = new MockMultipartFile("data",
                "filename.txt",
                "text/plain",
                "some content".getBytes());

        ReflectionTestUtils.setField(documentService, "maxFileSize", 8L);

        assertThrows(FileUploadException.class, () -> documentService.uploadFile(file, "Andrey"));
        verify(documentRepository, times(0)).save(any());
    }

    @Test
    public void getAllDocsTest() {
        PlatformUser platformUser = generateTestUser();
        platformUser.setId(1L);

        when(platformUserService.findPlatformUserByLogin("Andrey")).thenReturn(platformUser);

        Document document = generateTestDocument();
        document.setUser(platformUser);

        List<Document> list = List.of(document);

        when(documentRepository.findAllByUserId(1L)).thenReturn(List.of(document));

        List<Document> userList = documentService.getAllByUserLogin("Andrey");

        Assertions.assertEquals(list, userList);
    }

    @Test
    public void downloadTest() {
        Document document = generateTestDocument();

        when(documentRepository.getById(1L)).thenReturn(document);

        Document userDocument = documentService.downloadFile(1L);

        Assertions.assertEquals(userDocument, document);
    }

    @Test
    public void deleteTest() {
        Document document = generateTestDocument();

        documentService.delete(1L);

        verify(documentRepository, times(1)).deleteById(document.getId());
    }
}
