package com.project.trippilot.tool;


import com.project.trippilot.service.TavilyService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class TavilyTools {
    private final TavilyService tavilyService;

    public TavilyTools(TavilyService tavilyService) {
        this.tavilyService = tavilyService;
    }

    @Tool(
            name = "tavilySearch",
            description = """
            Search the web using Tavily.
            Use this tool when up-to-date web information is required,
            such as finding airports, locations, travel information,
            current events, or other information that may have changed.
            """
    )
    public String search(
            @ToolParam(description = "The web search query") String query) {

        return tavilyService.search(query);
    }

}
