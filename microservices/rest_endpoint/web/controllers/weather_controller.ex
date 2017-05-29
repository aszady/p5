defmodule RestEndpoint.WeatherController do
  use RestEndpoint.Web, :controller

  def get_weather(conn, params) do
    position = {params["latitude"], params["longtitude"]}
    case get_cache(position) do
      nil -> 
        res = calculate_weather(position)
        conn
        |> json(res)
      {:error, reason} ->
        conn
        |> put_status(500)
        |> json(%{result: :error, message: inspect(reason)}) 
      val ->
        conn
        |> json(%{result: val})
    end
  end

  defp get_cache(position) do
    RestEndpoint.Notifier.get(:cache, position)
  end

  defp calculate_weather(position) do
    case RestEndpoint.Notifier.get(:calculator, position) do
      {:error, reason} -> %{result: :error, message: inspect(reason)}
      val -> %{result: val}
    end
  end
end
