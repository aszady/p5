defmodule RestEndpoint.Http do

  require Logger

  def post(url, data) do
    case HTTPoison.post(url, data) do
      {:ok, res} -> Poison.decode!(res.body)
      {:error, reason} ->
        Logger.warn "ERROR: #{inspect(reason)}"
        {:error, reason}
    end
  end
end
