/**
 * 
 */
package br.com.xisp.utils;

import java.util.List;

import br.com.xisp.models.Story;

/**
 * @author edipo
 *	Classe que calcula a media de dias levados nas estorias finalizadas.
 */
public class StoryUtil {
	
	public static String calculeAvgForStories(List<Story> list) {
		long time = 0L;
		int numberStoriesFinished = 0;
		for (Story story : list) {
			//Assegura que somente estorias finalizadas irao ser utilizadas no calculo
			if(story.getEndAt() != null){
				time += DateDifference.diferenceInDays(story.getEndAt(), story.getStartedAt());
				numberStoriesFinished++;
			}
		}
		if(numberStoriesFinished > 0)
			return "Uma media de " + Math.abs(time/numberStoriesFinished) + " dias para finalizar " + numberStoriesFinished + " estorias";
		else
			return "";
		}
}
