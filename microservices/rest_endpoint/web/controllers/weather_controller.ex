defmodule RestEndpoint.WeatherController do
  use RestEndpoint.Web, :controller

  def get_weather(conn, params) do
    res = calculate_weather(params)
    conn
    |> json(res)
  end

  defp calculate_weather(position) do
    case RestEndpoint.Notifier.get(:calculator, position) do
      {:error, reason} -> %{result: :error, message: inspect(reason)}
      val -> %{result: val}
    end
  end
end
