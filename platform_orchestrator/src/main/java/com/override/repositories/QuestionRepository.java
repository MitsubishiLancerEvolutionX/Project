package com.override.repositories;

import com.override.models.PlatformUser;
import com.override.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByUserAndChapter(PlatformUser platformUser, String chapter);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Question q set q.answered = :answered where q.id = :id")
    void patchAnsweredById(@Param("answered") boolean answered, @Param("id") long id);
}
