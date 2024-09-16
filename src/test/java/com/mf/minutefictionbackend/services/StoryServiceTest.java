package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.enums.StoryStatus;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.Theme;
import com.mf.minutefictionbackend.repositories.AuthorProfileRepository;
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
    private AuthorProfileRepository authorProfileRepository;

    @InjectMocks
    private StoryService storyService;

    private Story story;
    private Story story2;
    private Story story3;
    private Story story4;
    private Story story5;
    private Story story6;
    private Theme theme;
    private Theme theme2;
    private Theme themeClosed;


    @BeforeEach
    public void setUp() {


        theme = Theme.builder().id(2L).name("Theme 2")
                .closingDate(LocalDate.of(2024, 8, 30))
                .build();

        theme2 = Theme.builder().id(1L).name("Theme 1")
                .closingDate(LocalDate.of(2024, 12, 30))
                .build();

        themeClosed = Theme.builder()
                .id(3L)
                .name("Theme Old")
                .closingDate(LocalDate.of(2024, 2, 28))
                .build();

        story = Story.builder()
                .id(1L)
                .title("Story 1")
                .content("Content story one...")
                .status(SUBMITTED)
                .publishDate(null)
                .author(AuthorProfile.builder().username("user1").firstname("firstname")
                        .lastname("lastname").build())
                .theme(theme2)
                .build();

        story2 = Story.builder()
                .id(2L)
                .title("Story 2")
                .content("Content story two...")
                .status(ACCEPTED)
                .publishDate(null)
                .author(AuthorProfile.builder().username("user1").firstname("firstname")
                        .lastname("lastname").build())
                .theme(theme)
                .build();

        story3 = Story.builder()
                .id(3L)
                .title("Story 3")
                .content("Content story three...")
                .status(SUBMITTED)
                .publishDate(null)
                .author(AuthorProfile.builder().username("user2").firstname("secondfirst")
                        .lastname("secondlast").build())
                .theme(theme2)
                .build();

        LocalDate publishDate = LocalDate.of(2024, 3, 1);

        story4 = Story.builder()
                .id(4L)
                .title("Story 4")
                .content("Content story four...")
                .status(PUBLISHED)
                .publishDate(publishDate)
                .author(AuthorProfile.builder().username("user3").firstname("thirdfirst")
                        .lastname("thirdlast").build())
                .theme(themeClosed)
                .build();

        story5 = Story.builder()
                .id(5L)
                .title("Story 5")
                .content("Content story five...")
                .status(PUBLISHED)
                .publishDate(publishDate)
                .author(AuthorProfile.builder().username("user1").firstname("firstname")
                        .lastname("lastname").build())
                .theme(themeClosed)
                .build();

        story6 = Story.builder()
                .id(6L)
                .title("Story 6")
                .content("Content story six...")
                .status(ACCEPTED)
                .publishDate(publishDate)
                .author(AuthorProfile.builder().username("user4").firstname("fourthname")
                        .lastname("fourthname").build())
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
        theme = null;
        theme2 = null;
        themeClosed = null;

    }

    @Test
    @DisplayName("Should save correct story")
    void submitStoryTest() {

        Long themeId = 1L;
        String username = "user0";

        StoryInputDto storyInputDto = new StoryInputDto();
        storyInputDto.setTitle("Submit story");
        storyInputDto.setContent("Content submit story...");

        AuthorProfile authorProfile = AuthorProfile.builder().username(username).build();

        Theme theme = Theme.builder().id(themeId).name("How to submit")
                .closingDate(LocalDate.now().plusDays(10)).build();

        Mockito.when(authorProfileRepository.findById(username)).thenReturn(Optional.of(authorProfile));
        Mockito.when(themeRepository.findById(themeId)).thenReturn(Optional.of(theme));
        Mockito.when(storyRepository.countSubmissionsByTheme(theme)).thenReturn(0);
        Mockito.when(storyRepository.existsByThemeAndAuthorUsername(theme, username)).thenReturn(false);
        Mockito.when(storyRepository.save(Mockito.any(Story.class))).thenAnswer(invocation -> invocation.<Story>getArgument(0));

        StoryOutputDto createdStory = storyService.submitStory(storyInputDto, themeId, username);

        assertEquals("Submit story", createdStory.getTitle());
        assertEquals("Content submit story...", createdStory.getContent());
        assertEquals("user0", createdStory.getUsername());

    }


    @Test
    @DisplayName("Should return correct updated story")
    void updateStoryTest() {

        Mockito.when(storyRepository.findById(story.getId())).thenReturn(Optional.of(story));

        StoryInputDto storyInputDto = new StoryInputDto();
        storyInputDto.setContent("Content updated...");

        Mockito.when(storyRepository.save(Mockito.any(Story.class))).thenReturn(story);

        StoryOutputDto updatedStory = storyService.updateStory(story.getId(), storyInputDto);

        assertEquals(1L, story.getId());
        assertEquals("Content updated...", updatedStory.getContent());
        Mockito.verify(storyRepository, Mockito.times(1)).save(story);


    }

    @Test
    @DisplayName("Should publish the correct story")
    void publishStoryTest() {

        Mockito.when(storyRepository.findById(story2.getId())).thenReturn(Optional.of(story2));

        storyService.publishStory(story2.getId());

        assertEquals(2L, story2.getId());
        assertEquals(PUBLISHED, story2.getStatus());
        Mockito.verify(storyRepository, Mockito.times(1)).save(story2);

    }

    @Test
    @DisplayName("Should change the status of the correct story from submitted to accepted")
    void acceptStoryTest() {

        Mockito.when(storyRepository.findById(story.getId())).thenReturn(Optional.of(story));

        storyService.acceptStory(story.getId());

        assertEquals(2L, story2.getId());
        assertEquals(StoryStatus.ACCEPTED, story.getStatus());
        Mockito.verify(storyRepository, Mockito.times(1)).save(story);

    }

    @Test
    @DisplayName("Should publish only accepted stories by the correct theme")
    void publishAllStoriesByStatusAndThemeTest() {

        Long themeId = 2L;
        List<Story> mockStoriesToPublish = List.of(story2, story6);

        Mockito.when(themeRepository.findById(themeId)).thenReturn(Optional.of(theme));
        Mockito.when(storyRepository.findByStatusAndTheme(StoryStatus.ACCEPTED, theme)).thenReturn(mockStoriesToPublish);

        storyService.publishAllStoriesByStatusAndTheme(2L);

        assertEquals(PUBLISHED, story2.getStatus());
        assertEquals(LocalDate.now(), story2.getPublishDate());
        assertEquals(PUBLISHED, story6.getStatus());
        assertEquals(LocalDate.now(), story6.getPublishDate());

        Mockito.verify(storyRepository, Mockito.times(1)).saveAll(mockStoriesToPublish);

    }

    @Test
    @DisplayName("Should change the status of the correct story from submitted to declined")
    void declineStoryTest() {

        Mockito.when(storyRepository.findById(story.getId())).thenReturn(Optional.of(story));

        storyService.declineStory(story.getId());

        assertEquals("Story 1", story.getTitle());
        assertEquals(DECLINED, story.getStatus());
        Mockito.verify(storyRepository, Mockito.times(1)).save(story);

    }

    @Test
    @DisplayName("Should delete the correct story")
    void deleteStoryByIdTest() {

        Mockito.when(storyRepository.findById(story.getId())).thenReturn(Optional.of(story));

        storyService.deleteStoryById(story.getId());

        assertEquals(StoryStatus.DECLINED, story.getStatus());
        Mockito.verify(storyRepository, Mockito.times(1)).save(story);

    }

    @Test
    @DisplayName("Should return only stories with submitted status")
    void getStoriesBySubmittedStatusTest() {

        List<Story> mockStoryList = List.of(story, story3);
        Mockito.when(storyRepository.findByStatusOrderByPublishDateDesc(SUBMITTED)).thenReturn(List.of(story, story3));

        List<StoryOutputDto> storyList = storyService.getStoriesByStatus(SUBMITTED);

        assertEquals(storyList.size(), mockStoryList.size());
        assertEquals(story.getTitle(), storyList.get(0).getTitle());
        assertEquals(story3.getTitle(), storyList.get(1).getTitle());

    }

    @Test
    @DisplayName("Should return only stories with published status")
    void getStoriesByPublishedStatusTest() {

        List<Story> mockStoryList = List.of(story4, story5);
        Mockito.when(storyRepository.findByStatusOrderByPublishDateDesc(PUBLISHED)).thenReturn(List.of(story4, story5));

        List<StoryOutputDto> storyList = storyService.getStoriesByStatus(PUBLISHED);

        assertEquals(storyList.size(), mockStoryList.size());
        assertEquals(story4.getTitle(), storyList.get(0).getTitle());
        assertEquals(story5.getTitle(), storyList.get(1).getTitle());
    }


    @Test
    @DisplayName("Should return correct submitted story")
    void getSubmittedStoryById() {

        Mockito.when(storyRepository.findById(story.getId())).thenReturn(Optional.of(story));

        StoryOutputDto storyDto = storyService.getSubmittedStoryById(story.getId());

        assertEquals("user1", storyDto.getUsername());
        assertEquals("Theme 1", storyDto.getThemeName());
        assertEquals("Story 1", storyDto.getTitle());
        assertEquals("Content story one...", storyDto.getContent());
    }

    @Test
    @DisplayName("Should return stories with correct status and themeId")
    void getStoriesByStatusAndThemeId() {

        List<Story> mockStoryList = List.of(story4, story5);

        Mockito.when(themeRepository.findById(themeClosed.getId())).thenReturn(Optional.of(themeClosed));
        Mockito.when(storyRepository.findByStatusAndTheme(PUBLISHED, themeClosed)).thenReturn(List.of(story4, story5));

        List<StoryOutputDto> storyList = storyService.getStoriesByStatusAndThemeId(PUBLISHED, themeClosed.getId());

        assertEquals(storyList.size(), mockStoryList.size());
        assertEquals(story4.getTitle(), storyList.get(0).getTitle());
        assertEquals(story5.getTitle(), storyList.get(1).getTitle());
        assertEquals(story4.getTheme().getName(), storyList.get(0).getThemeName());
        assertEquals(story5.getTheme().getName(), storyList.get(1).getThemeName());
    }

    @Test
    @DisplayName("Should return stories by correct status and theme name")
    void getStoriesByStatusAndThemeName() {

        List<Story> mockStoryList = List.of(story4, story5);

        Mockito.when(themeRepository.findByNameIgnoreCase(themeClosed.getName())).thenReturn(Optional.of(themeClosed));
        Mockito.when(storyRepository.findByStatusAndTheme(PUBLISHED, themeClosed)).thenReturn(List.of(story4, story5));

        List<StoryOutputDto> storyList = storyService.getStoriesByStatusAndThemeName(PUBLISHED, themeClosed.getName());

        assertEquals(storyList.size(), mockStoryList.size());
        assertEquals(story4.getTitle(), storyList.get(0).getTitle());
        assertEquals(story5.getTitle(), storyList.get(1).getTitle());
        assertEquals(story4.getTheme().getName(), storyList.get(0).getThemeName());
        assertEquals(story5.getTheme().getName(), storyList.get(1).getThemeName());

    }

    @Test
    @DisplayName("Should return all stories of the correct author")
    void getAllStoriesByAuthorTest() {

        List<Story> mockStoryList = List.of(story, story2, story5);
        Mockito.when(storyRepository.findByAuthor_Username(story.getAuthor().getUsername())).thenReturn(List.of(story, story2, story5));

        List<StoryOutputDto> storyList = storyService.getAllStoriesByAuthor("user1");

        assertEquals(storyList.size(), mockStoryList.size());
        assertEquals(story.getAuthor().getUsername(), mockStoryList.get(0).getAuthor().getUsername());
        assertEquals(story.getTitle(), storyList.get(0).getTitle());
        assertEquals(story5.getTitle(), storyList.get(2).getTitle());

    }

    @Test
    @DisplayName("Should return only published stories of the correct author")
    void getPublishedStoriesByAuthorTest() {

        List<Story> mockStoryList = List.of(story5);
        Mockito.when(storyRepository.findByAuthor_UsernameAndStatus(story5.getAuthor().getUsername(), PUBLISHED)).thenReturn(List.of(story5));

        List<StoryOutputDto> storyList = storyService.getPublishedStoriesByAuthor(story5.getAuthor().getUsername());

        assertEquals(storyList.size(), mockStoryList.size());
        assertEquals(story5.getAuthor().getUsername(), storyList.get(0).getUsername());
        assertEquals(story5.getTitle(), storyList.get(0).getTitle());

    }


    @Test
    @DisplayName("Should return correct story")
    void getStoryByStatusAndStoryIdTest() {

        Mockito.when(storyRepository.findByStatusAndId(PUBLISHED, story5.getId())).thenReturn(Optional.of(story5));

        StoryOutputDto storyDto = storyService.getStoryByStatusAndStoryId(story5.getStatus(), story5.getId());

        assertEquals(story5.getId(), storyDto.getId());
        assertEquals(story5.getStatus(), storyDto.getStatus());
        assertEquals(story5.getAuthor().getUsername(), storyDto.getUsername());

    }
}