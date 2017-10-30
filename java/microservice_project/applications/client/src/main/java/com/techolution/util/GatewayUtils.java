package com.techolution.util;

import com.techolution.skill.AssessmentQuestion;
import com.techolution.skill.Question;
import com.techolution.skill.QuestionComparatorByPriority;
import com.techolution.skill.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Dharmendra Pandit on 6/28/2017.
 */
@Component
public class GatewayUtils {

    public static String DELETE_POSITION_WARNING_MESSAGE = "The position you are trying to delete is included in {0} interviews and {1} assessments.The interviews and assessments will be deleted as well. Are you sure you want to delete it?";
    public static String DELETE_POSITION_SUCCESS_MESSAGE = "Position has been deleted successfully.";
    public static String OOPS_WARNING = "Oops! Something went wrong. Try after sometime.";
    public static final String HTTP_STR = "http://";
    public static final String COLON = ":";
    public static final String SLASH = "/";

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * Builds the URL
     *
     * @param applicationName
     * @param path
     * @return url
     */
    public String buildUrl(final String applicationName, final String path) {
        final StringBuilder builder = new StringBuilder(30);
        if (StringUtils.hasText(applicationName) && StringUtils.hasText(path)) {
            final ServiceInstance serviceInstance = loadBalancerClient.choose(applicationName);

            if (serviceInstance != null) {
                builder.append(HTTP_STR).append(serviceInstance.getHost())
                    .append(COLON).append(serviceInstance.getPort());

                if (!path.startsWith(SLASH)) {
                    builder.append(SLASH);
                }
                builder.append(path);
            }
        }
        return builder.toString();
    }

    /**
     * @param name
     * @param path
     * @param version
     * @return SwaggerResource
     */
    public SwaggerResource buildSwaggerResource(final String name, final String path, final String version) {
        final SwaggerResource swaggerResource = new SwaggerResource();
        final String location = buildUrl(name, path);
        swaggerResource.setLocation(location);
        swaggerResource.setName(name);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

    public Skill getSkillWithSortedQuestions(Skill skill) {
        if (skill != null) {
            Set<Question> questions = new TreeSet<>(new QuestionComparatorByPriority());
            for (Question qs : skill.getQuestions()) {
                questions.add(qs);
            }
            skill.setQuestions(questions);
        }
        return skill;
    }

    public AssessmentQuestion getAssessmentQuestion(final Skill skill, final String assessmentId) {

        Set<AssessmentQuestion> assessmentQuestions = skill.getAssessmentQuestions();
        AssessmentQuestion assessmentQuestion = null;
        if(assessmentQuestions != null) {
            for(AssessmentQuestion assessmentQuestionDB : assessmentQuestions) {
                if(assessmentId.equals(assessmentQuestionDB.getId())) {
                    assessmentQuestion = assessmentQuestionDB;
                    break;
                }
            }
        }
        return assessmentQuestion;
    }

}
