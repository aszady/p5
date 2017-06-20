defmodule RestEndpoint.Http do

  require Logger

  def get(url) do
    get(url, [])
  end

  def get(url, headers) do
    case HTTPoison.get(url, headers) do
      {:ok, res} -> Poison.decode!(res.body)
      {:error, reason} ->
        Logger.warn "ERROR: #{inspect(reason)}"
        {:error, reason}
    end
  end

end
