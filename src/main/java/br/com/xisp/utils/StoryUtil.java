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
		int numberStories = 0;
		for (Story story : list) {
			//Assegura que somente estorias finalizadas irao ser utilizadas no calculo
			if(story.getEndAt() != null){
				time += DateDifference.diferenceInDays(story.getEndAt(), story.getStartedAt());
				numberStories++;
			}
		}
		return "Media de dias estorias finalizadas: " + Math.abs(time/numberStories) + ".";
	}
}
