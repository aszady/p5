defmodule RestEndpoint.GeoPositionController do
  use RestEndpoint.Web, :controller

  def update(conn, params) do
    position = params["position"]
    notify_logger(position)
    conn
    |> json(%{results: :ok})
  end

  defp notify_logger(position) do
    RestEndpoint.Notifier.notify(:logger, position)
  end
end
