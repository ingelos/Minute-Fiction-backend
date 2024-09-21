package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.enums.StoryStatus;
import com.mf.minutefictionbackend.exceptions.BadRequestException;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.models.Comment;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.Theme;
import com.mf.minutefictionbackend.repositories.AuthorProfileRepository;
import com.mf.minutefictionbackend.repositories.CommentRepository;
import com.mf.minutefictionbackend.repositories.StoryRepository;
import com.mf.minutefictionbackend.repositories.ThemeRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.mf.minutefictionbackend.enums.StoryStatus.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StoryServiceTest {

    @Mock
    private StoryRepository storyRepository;
    @Mock
    private ThemeRepository themeRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private AuthorProfileRepository authorProfileRepository;

    @InjectMocks
    private StoryService storyService;

    private Story story;
    private Story story2;
    private Story story3;
    private Story story4;
    private Story story5;
    private Story story6;
    private Story story7;
    private Theme theme;
    private Theme theme2;
    private Theme themeClosed;
    private Comment comment1;
    private Comment comment2;


    @BeforeEach
    public void setUp() {


        theme = Theme.builder().id(1L).name("Theme 1")
                .closingDate(LocalDate.now().plusDays(10))
                .build();

        theme2 = Theme.builder().id(2L).name("Theme 2")
                .closingDate(LocalDate.of(2024, 12, 30))
                .build();

        themeClosed = Theme.builder().id(3L).name("Theme Old")
                .closingDate(LocalDate.of(2024, 2, 28))
                .build();

        story = Story.builder()
                .id(1L)
                .title("Story 1")
                .content("Content story one...")
                .status(SUBMITTED)
                .publishDate(null)
                .author(AuthorProfile.builder().username("user1").build())
                .theme(theme2)
                .build();

        story2 = Story.builder()
                .id(2L)
                .title("Story 2")
                .content("Content story two...")
                .status(ACCEPTED)
                .publishDate(null)
                .author(AuthorProfile.builder().username("user1").build())
                .theme(theme)
                .build();

        story3 = Story.builder()
                .id(3L)
                .title("Story 3")
                .content("Content story three...")
                .status(SUBMITTED)
                .publishDate(null)
                .author(AuthorProfile.builder().username("user2").build())
                .theme(theme2)
                .build();

        LocalDate publishDate1 = LocalDate.of(2024, 2, 1);

        story4 = Story.builder()
                .id(4L)
                .title("Story 4")
                .content("Content story four...")
                .status(PUBLISHED)
                .publishDate(publishDate1)
                .author(AuthorProfile.builder().username("user3").build())
                .theme(themeClosed)
                .build();

        comment1 = Comment.builder().id(1L).content("Comment 1").created(LocalDateTime.now()).build();
        comment2 = Comment.builder().id(2L).content("Comment 2").created(LocalDateTime.now()).build();
        List<Comment> comments = List.of(comment1, comment2);

        LocalDate publishDate2 = LocalDate.of(2024, 3, 1);

        story5 = Story.builder()
                .id(5L)
                .title("Story 5")
                .content("Content story five...")
                .status(PUBLISHED)
                .publishDate(publishDate2)
                .author(AuthorProfile.builder().username("user1").build())
                .theme(themeClosed)
                .comments(comments)
                .build();

        story6 = Story.builder()
                .id(6L)
                .title("Story 6")
                .content("Content story six...")
                .status(ACCEPTED)
                .publishDate(null)
                .author(AuthorProfile.builder().username("user4").build())
                .theme(theme)
                .build();

        story7 = Story.builder()
                .id(7L)
                .title("Story 7")
                .content("Content story seven...")
                .status(SUBMITTED)
                .publishDate(null)
                .author(AuthorProfile.builder().username("user5").build())
                .theme(theme)
                .build();
    }

    @AfterEach
    void tearDown() {

        story = null;
        story2 = null;
        story3 = null;
        story4 = null;
        story5 = null;
        story6 = null;
        story7 = null;
        theme = null;
        theme2 = null;
        themeClosed = null;
        comment1 = null;
        comment2 = null;
    }


    @Test
    @DisplayName("Should save correct story")
    void submitStoryTest() {

        String username = "user0";

        StoryInputDto storyInputDto = new StoryInputDto();
        storyInputDto.setTitle("Submit story");
        storyInputDto.setContent("Content submit story...");

        AuthorProfile authorProfile = AuthorProfile.builder().username(username).build();
        
        Mockito.when(authorProfileRepository.findById(username)).thenReturn(Optional.of(authorProfile));
        Mockito.when(themeRepository.findById(theme.getId())).thenReturn(Optional.of(theme));
        Mockito.when(storyRepository.countSubmissionsByTheme(theme)).thenReturn(0);
        Mockito.when(storyRepository.existsByThemeAndAuthorUsername(theme, username)).thenReturn(false);
        Mockito.when(storyRepository.save(Mockito.any(Story.class))).thenAnswer(invocation -> invocation.<Story>getArgument(0));

        StoryOutputDto createdStory = storyService.submitStory(storyInputDto, theme.getId(), username);

        assertEquals("Submit story", createdStory.getTitle());
        assertEquals("Content submit story...", createdStory.getContent());
        assertEquals("user0", createdStory.getUsername());

    }

    @Test
    @DisplayName("Should throw bad request when user tries to submit a second story to a theme")
    void submitStoryShouldThrowBadRequestWhenAlreadySubmittedToThemeTest() {

        String username = "user1";

        StoryInputDto storyInputDto = new StoryInputDto();
        storyInputDto.setTitle("Submit a second story");
        storyInputDto.setContent("Content second submitted story...");

        AuthorProfile authorProfile = AuthorProfile.builder().username(username).build();

        Mockito.when(authorProfileRepository.findById(username)).thenReturn(Optional.of(authorProfile));
        Mockito.when(themeRepository.findById(theme2.getId())).thenReturn(Optional.of(theme2));
        Mockito.when(storyRepository.countSubmissionsByTheme(theme2)).thenReturn(1);

        Mockito.when(storyRepository.existsByThemeAndAuthorUsername(theme2, username)).thenReturn(true);

        assertThrows(BadRequestException.class, () -> storyService.submitStory(storyInputDto, theme2.getId() ,"user1"));

    }


    @Test
    @DisplayName("Should return correct updated story")
    void updateStoryTest() {

        Long storyId = 1L;

        StoryInputDto storyInputDto = new StoryInputDto();
        storyInputDto.setContent("Content updated...");

        Mockito.when(storyRepository.findById(storyId)).thenReturn(Optional.of(story));
        Mockito.when(storyRepository.save(Mockito.any(Story.class))).thenReturn(story);

        StoryOutputDto updatedStory = storyService.updateStory(storyId, storyInputDto);

        assertEquals(storyId, updatedStory.getId());
        assertEquals(story.getContent(), updatedStory.getContent());
        Mockito.verify(storyRepository, Mockito.times(1)).save(story);
    }


    @Test
    @DisplayName("Should return stories with correct status and themeId")
    void getStoriesByStatusAndThemeIdTest() {

        Long themeId = 3L;
        String titleStory4 = "Story 4";
        String titleStory5 = "Story 5";
        String themeName = "Theme Old";
        List<Story> mockStoryList = List.of(story4, story5);

        Mockito.when(themeRepository.findById(themeId)).thenReturn(Optional.of(themeClosed));
        Mockito.when(storyRepository.findByStatusAndTheme(PUBLISHED, themeClosed)).thenReturn(List.of(story4, story5));

        List<StoryOutputDto> storyList = storyService.getStoriesByStatusAndThemeId(PUBLISHED, themeId);

        assertEquals(storyList.size(), mockStoryList.size());
        assertEquals(titleStory4, storyList.get(0).getTitle());
        assertEquals(titleStory5, storyList.get(1).getTitle());
        assertEquals(themeName, storyList.get(0).getThemeName());
        assertEquals(themeName, storyList.get(1).getThemeName());
    }



    @Test
    @DisplayName("Should get correct story by id")
    void getStoryByIdTest() {

        Long storyId = 5L;
        String username = "user1";
        String theme = "Theme Old";
        String storyTitle = "Story 5";
        String storyContent = "Content story five...";
        Mockito.when(storyRepository.findById(storyId)).thenReturn(Optional.of(story5));

        StoryOutputDto storyDto = storyService.getStoryById(storyId);

        assertEquals(username, storyDto.getUsername());
        assertEquals(theme, storyDto.getThemeName());
        assertEquals(storyTitle, storyDto.getTitle());
        assertEquals(storyContent, storyDto.getContent());
    }


    @Test
    @DisplayName("Should delete the correct story")
    void deleteStoryWithoutCommentsByIdTest() {

        Long storyId = 1L;
        Mockito.when(storyRepository.findById(storyId)).thenReturn(Optional.of(story));

        storyService.deleteStoryById(storyId);

        Mockito.verify(commentRepository, Mockito.never()).deleteAll(Mockito.anyList());
        Mockito.verify(storyRepository).delete(story);

    }

    @Test
    @DisplayName("Should delete the correct story and associated comments")
    void deleteStoryWithCommentsByIdTest() {

        Long storyId = 5L;
        Mockito.when(storyRepository.findById(storyId)).thenReturn(Optional.of(story5));

        List<Comment> comments = story5.getComments();

        storyService.deleteStoryById(storyId);

        Mockito.verify(commentRepository).deleteAll(comments);
        Mockito.verify(storyRepository).delete(story5);
        Mockito.verify(storyRepository, Mockito.times(1)).delete(story5);

    }




    @Test
    @DisplayName("Should change the status of the correct story from submitted to accepted")
    void acceptStoryTest() {

        Long storyId = 1L;
        Mockito.when(storyRepository.findById(storyId)).thenReturn(Optional.of(story));

        storyService.acceptStory(storyId);

        assertEquals(storyId, story.getId());
        assertEquals(ACCEPTED, story.getStatus());
        Mockito.verify(storyRepository, Mockito.times(1)).save(story);

    }


    @Test
    @DisplayName("Should change the status of the correct story from submitted to declined")
    void declineStoryTest() {

        Long storyId = 1L;
        Mockito.when(storyRepository.findById(storyId)).thenReturn(Optional.of(story));

        storyService.declineStory(storyId);

        assertEquals(DECLINED, story.getStatus());
        Mockito.verify(storyRepository, Mockito.times(1)).save(story);

    }

    @Test
    @DisplayName("Should publish the correct story and set status to published")
    void publishStoryTest() {

        Long storyId = 2L;
        Mockito.when(storyRepository.findById(storyId)).thenReturn(Optional.of(story2));

        storyService.publishStory(storyId);

        assertEquals(storyId, story2.getId());
        assertEquals(PUBLISHED, story2.getStatus());
        Mockito.verify(storyRepository, Mockito.times(1)).save(story2);

    }



    @Test
    @DisplayName("Should publish only accepted stories by the correct theme")
    void publishAllStoriesByStatusAndThemeTest() {

        Long themeId = 2L;
        List<Story> mockStoriesToPublish = List.of(story2, story6);

        Mockito.when(themeRepository.findById(themeId)).thenReturn(Optional.of(theme));
        Mockito.when(storyRepository.findByStatusAndTheme(StoryStatus.ACCEPTED, theme)).thenReturn(mockStoriesToPublish);

        storyService.publishAllAcceptedStoriesByTheme(themeId);

        assertEquals(PUBLISHED, story2.getStatus());
        assertEquals(LocalDate.now(), story2.getPublishDate());
        assertEquals(PUBLISHED, story6.getStatus());
        assertEquals(LocalDate.now(), story6.getPublishDate());
        Mockito.verify(storyRepository, Mockito.times(1)).saveAll(mockStoriesToPublish);
    }


    @Test
    @DisplayName("Should return correct stories in order of publish date")
    void getAllPublishedStoriesByPublishDateDescTest() {

        String titleStory4 = "Story 4";
        String titleStory5 = "Story 5";

        LocalDate publishDateStory4 = LocalDate.of(2024, 2, 1);
        LocalDate publishDateStory5 = LocalDate.of(2024, 3, 1);

        List<Story> mockStoryList = List.of(story4, story5);
        Mockito.when(storyRepository.findByStatusOrderByPublishDateDesc(PUBLISHED)).thenReturn(List.of(story5, story4));

        List<StoryOutputDto> storyList = storyService.getAllPublishedStoriesByDateDesc(PUBLISHED);

        assertEquals(storyList.size(), mockStoryList.size());
        assertEquals(titleStory4, storyList.get(1).getTitle());
        assertEquals(titleStory5, storyList.get(0).getTitle());
        assertEquals(publishDateStory4, storyList.get(1).getPublishDate());
        assertEquals(publishDateStory5, storyList.get(0).getPublishDate());
    }


    @Test
    @DisplayName("Should return correct story")
    void getStoryByStatusAndStoryIdTest() {

        Long storyId = 5L;
        String username = "user1";

        Mockito.when(storyRepository.findByStatusAndId(PUBLISHED, storyId)).thenReturn(Optional.of(story5));

        StoryOutputDto storyDto = storyService.getStoryByStatusAndStoryId(PUBLISHED, storyId);

        assertEquals(storyId, storyDto.getId());
        assertEquals(PUBLISHED, storyDto.getStatus());
        assertEquals(username, storyDto.getUsername());
    }


    @Test
    @DisplayName("Should return all stories related to theme, no matter what status")
    void getStoriesByThemeTest() {

        Long themeId = 1L;
        String titleStory2 = "Story 2";
        String titleStory6 = "Story 6";
        String themeStory6 = "Theme 1";
        String themeStory7 = "Theme 1";
        List<Story> mockStoryList = List.of(story2, story6, story7);

        Mockito.when(storyRepository.findByThemeId(themeId)).thenReturn(List.of(story2, story6, story7));

        List<StoryOutputDto> relatedStoryList = storyService.getStoriesByTheme(themeId);

        assertEquals(relatedStoryList.size(), mockStoryList.size());
        assertEquals(titleStory2, relatedStoryList.get(0).getTitle());
        assertEquals(titleStory6, relatedStoryList.get(1).getTitle());
        assertEquals(themeStory6, relatedStoryList.get(1).getThemeName());
        assertEquals(themeStory7, relatedStoryList.get(2).getThemeName());

    }

    @Test
    @DisplayName("Should return only stories with submitted status")
    void getStoriesBySubmittedStatusTest() {

        String titleStory = "Story 1";
        String titleStory3 = "Story 3";
        List<Story> mockStoryList = List.of(story, story3);
        Mockito.when(storyRepository.findByStatus(SUBMITTED)).thenReturn(List.of(story, story3));

        List<StoryOutputDto> storyList = storyService.getStoriesByStatus(SUBMITTED);

        assertEquals(storyList.size(), mockStoryList.size());
        assertEquals(titleStory, storyList.get(0).getTitle());
        assertEquals(titleStory3, storyList.get(1).getTitle());
        assertEquals(SUBMITTED, storyList.get(0).getStatus());
        assertEquals(SUBMITTED, storyList.get(1).getStatus());

    }

    @Test
    @DisplayName("Should return only stories with published status")
    void getStoriesByPublishedStatusTest() {

        String titleStory4 = "Story 4";
        String titleStory5 = "Story 5";
        List<Story> mockStoryList = List.of(story4, story5);
        Mockito.when(storyRepository.findByStatus(PUBLISHED)).thenReturn(List.of(story4, story5));

        List<StoryOutputDto> storyList = storyService.getStoriesByStatus(PUBLISHED);

        assertEquals(storyList.size(), mockStoryList.size());
        assertEquals(titleStory4, storyList.get(0).getTitle());
        assertEquals(titleStory5, storyList.get(1).getTitle());
    }


    @Test
    @DisplayName("Should return all stories of the correct author")
    void getAllStoriesByAuthorTest() {

        String username = "user1";
        String titleStory = "Story 1";
        String titleStory5 = "Story 5";
        List<Story> mockStoryList = List.of(story, story2, story5);
        Mockito.when(storyRepository.findByAuthor_Username(username)).thenReturn(List.of(story, story2, story5));

        List<StoryOutputDto> storyList = storyService.getAllStoriesByAuthor(username);

        assertEquals(storyList.size(), mockStoryList.size());
        assertEquals(username, storyList.get(0).getUsername());
        assertEquals(titleStory, storyList.get(0).getTitle());
        assertEquals(titleStory5, storyList.get(2).getTitle());
    }

    @Test
    @DisplayName("Should return only published stories of the correct author")
    void getPublishedStoriesByAuthorTest() {

        String username = "user1";
        String storyTitle = "Story 5";
        List<Story> mockStoryList = List.of(story5);
        Mockito.when(storyRepository.findByAuthor_UsernameAndStatus(username, PUBLISHED)).thenReturn(List.of(story5));

        List<StoryOutputDto> storyList = storyService.getPublishedStoriesByAuthor(username);

        assertEquals(storyList.size(), mockStoryList.size());
        assertEquals(username, storyList.get(0).getUsername());
        assertEquals(storyTitle, storyList.get(0).getTitle());

    }



}