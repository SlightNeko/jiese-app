package com.jiese.app.network

import com.jiese.app.data.model.AiAnalysis
import com.jiese.app.data.model.HrZones
import com.jiese.app.data.repository.AppRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ChatMessage(val role: String, val content: String)

@Serializable
data class ChatRequest(
    val model: String,
    val messages: List<ChatMessage>,
    @kotlinx.serialization.SerialName("response_format")
    val responseFormat: Map<String, String> = mapOf("type" to "json_object")
)

@Serializable
data class ChatChoice(val message: ChatMessage)

@Serializable
data class ChatResponse(val choices: List<ChatChoice>)

class AiApiClient(private val repository: AppRepository) {
    private val json = Json { ignoreUnknownKeys = true }
    private val httpClient = HttpClient {
        install(ContentNegotiation) { json(json) }
    }

    private val defaultSystemPrompt = """
你是一个运动科学分析助手，擅长根据详细跑步数据评估用户的健康收益。
你分析严谨、客观，回复简洁有用。
""".trimIndent()

    private val defaultUserPromptTemplate = """
请评估以下跑步记录的健康收益:

📊 基础数据
  距离: %.1f km
  时长: %.0f 分钟
  平均配速: %s

❤️ 心率数据
  平均心率: %s bpm
  最大心率: %s bpm

🏃 步频数据
  平均步频: %s spm
  最大步频: %s spm

📈 心率区间分布
  Z1 (<117): %d 分钟
  Z2 (117-136): %d 分钟
  Z3 (137-156): %d 分钟
  Z4 (157-170): %d 分钟
  Z5 (>170): %d 分钟

请用 JSON 格式回复，字段说明：
- health_score: 建议的额外加分数 (0~5)，在本地算法结果上做 ±调整
  需考虑：有氧收益、强度合理性、恢复压力、综合运动质量
- evaluation: "excellent" | "good" | "fair" | "poor"
- feedback: 一句话中文建议 (20 字以内)
""".trimIndent()

    suspend fun analyzeRunning(
        distanceKm: Double,
        durationMin: Double,
        avgHr: Int?,
        maxHr: Int?,
        avgPace: String?,
        avgCadence: Int?,
        maxCadence: Int?,
        hrZones: HrZones
    ): AiAnalysis? {
        val endpoint = repository.getSetting("api_endpoint") ?: return null
        val apiKey = repository.getSetting("api_key") ?: return null
        val model = repository.getSetting("model_name") ?: "gpt-4o-mini"

        val userPrompt = defaultUserPromptTemplate.format(
            distanceKm, durationMin,
            avgPace ?: "未知",
            avgHr?.toString() ?: "未知",
            maxHr?.toString() ?: "未知",
            avgCadence?.toString() ?: "未知",
            maxCadence?.toString() ?: "未知",
            hrZones.z1, hrZones.z2, hrZones.z3, hrZones.z4, hrZones.z5
        )

        try {
            val response: ChatResponse = httpClient.post(endpoint) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $apiKey")
                setBody(ChatRequest(
                    model = model,
                    messages = listOf(
                        ChatMessage("system", defaultSystemPrompt),
                        ChatMessage("user", userPrompt)
                    )
                ))
            }.body()

            val content = response.choices.firstOrNull()?.message?.content ?: return null
            return json.decodeFromString<AiAnalysis>(content)
        } catch (e: Exception) {
            return null
        }
    }
}
